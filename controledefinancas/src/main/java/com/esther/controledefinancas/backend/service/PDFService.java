package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.RelatorioGastoDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFService {
    public ByteArrayInputStream exportarRelatorioParaPDF(List<RelatorioGastoDTO> relatorio) {
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
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 3, 2});

            // Cabeçalhos
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Descrição", headFont));
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Valor", headFont));
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Forma de Pagamento", headFont));
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Status", headFont));
            table.addCell(hcell);

            // Dados
            for (RelatorioGastoDTO gasto : relatorio) {
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(gasto.getDescricao()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(gasto.getValor())));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(gasto.getFormaPagamento()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(gasto.getStatus()));
                table.addCell(cell);
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Erro ao gerar PDF: " + ex.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
