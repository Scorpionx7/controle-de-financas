package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.dto.RelatorioGastoDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Compra;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.model.enuns.FormaPagamento;
import com.esther.controledefinancas.backend.repository.CompraRepository;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private CartaoDeCreditoService cartaoService;

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ContaRepository contaRepository;

    public Compra registrarCompra(Compra compra) {
        if (compra.getFormaPagamento() == FormaPagamento.A_VISTA) {
            validarSaldoConta(compra.getConta().getId(), compra.getValor());
            contaService.atualizarSaldo(compra.getConta().getId(), -compra.getValor());
        } else if (compra.getFormaPagamento() == FormaPagamento.CARTAO) {
            if (compra.getParcelas() > 0) {
                double valorParcela = compra.getValor() / compra.getParcelas();
                validarLimiteCartao(compra.getCartao().getId(), compra.getValor());
                cartaoService.registrarCompra(compra.getCartao().getId(), compra.getValor());
                compra.setParcelasRestantes(compra.getParcelas());
            } else {
                validarLimiteCartao(compra.getCartao().getId(), compra.getValor());
                cartaoService.registrarCompra(compra.getCartao().getId(), compra.getValor());
            }
        } else {
            throw new RuntimeException("Forma de pagamento inválida.");
        }

        compra.setFinalizada(compra.getParcelas() == 0);
        compra.setData(LocalDate.now());
        return compraRepository.save(compra);
    }

    private void validarSaldoConta(Long contaId, double valor) {
        Conta conta = contaService.buscarContaPorId(contaId);
        if (conta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta para realizar a compra.");
        }
    }

    private void validarLimiteCartao(Long cartaoId, double valor) {
        CartaoDeCredito cartao = cartaoService.getCartaoById(cartaoId);
        if (cartao.getLimiteDisponivel() < valor) {
            throw new RuntimeException("Limite insuficiente no cartão para realizar a compra.");
        }
    }

    public List<RelatorioGastoDTO> gerarRelatorioGastos() {
        return compraRepository.findAll().stream()
                .map(compra -> new RelatorioGastoDTO(
                        compra.getDescricao(),
                        compra.getValor(),
                        compra.getFormaPagamento().toString(),
                        compra.isFinalizada() ? "Finalizada" : "Em aberto",
                        compra.getParcelas() > 0 ? compra.calcularValorParcela() : 0.0,
                        compra.getParcelasRestantes()
                ))
                .collect(Collectors.toList());
    }

    public List<RelatorioGastoDTO> gerarRelatorioGastosFiltrado(LocalDate dataInicio, LocalDate dataFim) {
        return compraRepository.findAll().stream()
                .filter(compra -> !compra.getData().isBefore(dataInicio) && !compra.getData().isAfter(dataFim))
                .map(compra -> new RelatorioGastoDTO(
                        compra.getDescricao(),
                        compra.getValor(),
                        compra.getFormaPagamento().toString(),
                        compra.isFinalizada() ? "Finalizada" : "Em aberto",
                        compra.getParcelas() > 0 ? compra.calcularValorParcela() : 0.0,
                        compra.getParcelasRestantes()
                ))
                .toList();
    }

    public ByteArrayInputStream exportarRelatorioParaExcel(List<RelatorioGastoDTO> relatorio) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Relatório de Gastos");

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Descrição", "Valor", "Forma de Pagamento", "Status", "Valor Parcela Atual", "Parcelas Restantes"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Dados
            int rowIdx = 1;
            for (RelatorioGastoDTO gasto : relatorio) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(gasto.getDescricao());
                row.createCell(1).setCellValue(gasto.getValor());
                row.createCell(2).setCellValue(gasto.getFormaPagamento());
                row.createCell(3).setCellValue(gasto.getStatus());
                row.createCell(4).setCellValue(gasto.getValorParcelaAtual());
                row.createCell(5).setCellValue(gasto.getParcelasRestantes());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportarRelatorioParaPDF(LocalDate dataInicio, LocalDate dataFim) {
        // Gera o relatório filtrado
        List<RelatorioGastoDTO> relatorio = gerarRelatorioGastosFiltrado(dataInicio, dataFim);

        // Cria o PDF a partir do relatório filtrado
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Relatório de Gastos", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Espaço
            document.add(Chunk.NEWLINE);

            // Tabela
            PdfPTable table = new PdfPTable(4); // Número de colunas
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 3, 2});

            // Cabeçalhos
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            String[] headers = {"Descrição", "Valor", "Forma de Pagamento", "Status"};
            for (String header : headers) {
                PdfPCell hcell = new PdfPCell(new Phrase(header, headFont));
                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(hcell);
            }

            // Dados
            for (RelatorioGastoDTO gasto : relatorio) {
                table.addCell(new Phrase(gasto.getDescricao()));
                table.addCell(new Phrase(String.format("R$ %.2f", gasto.getValor())));
                table.addCell(new Phrase(gasto.getFormaPagamento()));
                table.addCell(new Phrase(gasto.getStatus()));
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Erro ao gerar PDF: " + ex.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    // Método para processar o pagamento das parcelas mensalmente
    @Scheduled(cron = "0 0 0 1 * ?") // Executa no primeiro dia de cada mês
    public void processarPagamentoParcelas() {
        List<Compra> comprasParceladas = compraRepository.findAllByFinalizadaFalse();

        for (Compra compra : comprasParceladas) {
            double valorParcela = compra.calcularValorParcela();

            // Atualiza o limite ou saldo com base na forma de pagamento
            if (compra.getFormaPagamento() == FormaPagamento.CARTAO) {
                cartaoService.registrarCompra(compra.getCartao().getId(), -valorParcela); // Libera o limite
            } else if (compra.getFormaPagamento() == FormaPagamento.A_VISTA) {
                contaService.atualizarSaldo(compra.getConta().getId(), -valorParcela);
            }

            // Atualiza o número de parcelas restantes
            compra.setParcelasRestantes(compra.getParcelasRestantes() - 1);

            // Marca como finalizada se todas as parcelas foram pagas
            if (compra.getParcelasRestantes() <= 0) {
                compra.setFinalizada(true);
            }

            compraRepository.save(compra);
        }
    }

    public List<ContaDTO> listarTodas() {
        return contaRepository.findAll().stream()
                .map(conta -> new ContaDTO(conta.getId(),conta.getNome(), conta.getSaldo(), conta.getLimite(),conta.getValeAlimentacao()))
                .collect(Collectors.toList());
    }

}
