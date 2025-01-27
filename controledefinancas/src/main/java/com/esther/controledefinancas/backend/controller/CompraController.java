package com.esther.controledefinancas.backend.controller;

import com.esther.controledefinancas.backend.dto.RelatorioGastoDTO;
import com.esther.controledefinancas.backend.model.Compra;
import com.esther.controledefinancas.backend.repository.CompraRepository;
import com.esther.controledefinancas.backend.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;
    @Autowired
    private CompraRepository compraRepository;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Bem-vindo ao sistema financeiro! Utilize os endpoints disponíveis.");
    }
    @GetMapping
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    @PostMapping
    public Compra registrarCompra(@RequestBody Compra compra) {
        return compraService.registrarCompra(compra);
    }

    @GetMapping("/relatorios/todos")
    public List<RelatorioGastoDTO> gerarRelatorioGastos() {
        return compraService.gerarRelatorioGastos();
    }

    @GetMapping("/relatorios/filtrado")
    public List<RelatorioGastoDTO> gerarRelatorioFiltrado(
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim
    ) {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return compraService.gerarRelatorioGastosFiltrado(inicio, fim);
    }

    @GetMapping("/relatorios/exportar-excel")
    public ResponseEntity<byte[]> exportarRelatorioExcel(
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim
    ) throws IOException {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);

        List<RelatorioGastoDTO> relatorio = compraService.gerarRelatorioGastosFiltrado(inicio, fim);
        ByteArrayInputStream relatorioExcel = compraService.exportarRelatorioParaExcel(relatorio);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio_gastos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(relatorioExcel.readAllBytes());
    }

    @GetMapping("/relatorios/exportar-pdf")
    public ResponseEntity<byte[]> exportarRelatorioPDF(
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim
    ) throws IOException {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);

        ByteArrayInputStream pdf = compraService.exportarRelatorioParaPDF(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio_gastos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }

    @GetMapping("/finalizadas")
    public List<Compra> listarComprasFinalizadas() {
        return compraRepository.findAllByFinalizadaTrue();
    }

    @GetMapping("/pendentes")
    public List<Compra> listarComprasPendentes() {
        return compraRepository.findAllByFinalizadaFalse();
    }

    @PostMapping("/compras")
    public ResponseEntity<Compra> salvarCompra(@RequestBody Compra compra) {
        // Salva no banco
        Compra compraSalva = compraRepository.save(compra);
        return ResponseEntity.ok(compraSalva);
    }

}

