package com.esther.controledefinancas.backend.controller;

import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;



    @PostMapping
    public Conta criarConta(@RequestBody Conta conta) {
        return contaService.criarConta(conta);
    }

    @GetMapping("/{id}/saldo")
    public double consultarSaldo(@PathVariable Long id) {
        return contaService.consultarSaldo(id);
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> listarContas() {
        List<ContaDTO> contas = contaService.listarTodas();
        return ResponseEntity.ok(contas);
    }

    @PostMapping("/{contaId}/cartoes/{cartaoId}")
    public ResponseEntity<String> vincularCartaoAConta(@PathVariable Long contaId, @PathVariable Long cartaoId) {
        contaService.vincularCartao(contaId, cartaoId);
        return ResponseEntity.ok("Cartão vinculado à conta com sucesso!");
    }

    @GetMapping("/{id}") // Novo: Buscar conta por ID
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        return contaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}") // Novo: Atualizar Conta
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
        try {
            Conta contaAtualizada = contaService.atualizarConta(id, conta);
            return ResponseEntity.ok(contaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Ou um DTO de erro mais detalhado
        }
    }

    @DeleteMapping("/{id}") // Novo: Deletar Conta
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        try {
            contaService.deletarConta(id);
            return ResponseEntity.noContent().build(); // Resposta 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Se a conta não for encontrada
        }
    }

}
