package com.esther.controledefinancas.backend.controller;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import com.esther.controledefinancas.backend.service.CartaoDeCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoDeCreditoController {

    @Autowired
    CartaoDeCreditoRepository cartaoDeCreditoRepository;

    @Autowired
    private CartaoDeCreditoService cartaoService;

    @PostMapping("/criar")
    public CartaoDeCredito criarCartao(@RequestBody CartaoDeCredito cartao) {
        return cartaoService.criarCartao(cartao);
    }

    @GetMapping
    public ResponseEntity<List<CartaoDeCreditoDTO>> listarCartoes() {
        List<CartaoDeCreditoDTO> cartoes = cartaoService.listarTodos();
        return ResponseEntity.ok(cartoes);
    }

    @PostMapping //endpoint pode dar conflito//
    public ResponseEntity<CartaoDeCredito> salvarCartao(@RequestBody CartaoDeCredito cartao) {
        CartaoDeCredito cartaoSalvo = cartaoDeCreditoRepository.save(cartao);
        return ResponseEntity.ok(cartaoSalvo);
    }

    @PostMapping("/cartoes/{id}/quitar-parcela")
    public ResponseEntity<String> quitarParcela(
            @PathVariable Long id,
            @RequestParam double valorParcela) {
        try {
            cartaoService.quitarParcela(id, valorParcela);
            return ResponseEntity.ok("Parcela quitada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
