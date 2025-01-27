package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoDeCreditoService {

    @Autowired
    private CartaoDeCreditoRepository cartaoRepository;

    public CartaoDeCredito criarCartao(CartaoDeCredito cartao) {
        return cartaoRepository.save(cartao);
    }

    public List<CartaoDeCredito> listarCartoes() {
        return cartaoRepository.findAll();
    }

    public CartaoDeCredito buscarCartaoPorId(Long id) {
        return cartaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
    }

    public boolean registrarCompra(Long cartaoId, double valor) {
        CartaoDeCredito cartao = buscarCartaoPorId(cartaoId);
        if (cartao.registrarCompra(valor)) {
            cartaoRepository.save(cartao);
            return true;
        }
        throw new RuntimeException("Limite insuficiente no cartão.");
    }


    public List<CartaoDeCreditoDTO> listarTodos() {
        return cartaoRepository.findAll().stream()
                .map(cartao -> new CartaoDeCreditoDTO(cartao.getId(), cartao.getNome(), cartao.getLimite(), cartao.getGastoAtual()))
                .collect(Collectors.toList());
    }

    public CartaoDeCredito salvarCartao(CartaoDeCredito cartao) {
        if (cartao.getLimite() <= 0) {
            throw new IllegalArgumentException("O limite do cartão deve ser maior que zero.");
        }
        cartao.setGastoAtual(0.0); // Gasto inicial é zero
        return cartaoRepository.save(cartao);
    }
    public boolean quitarParcela(Long cartaoId, double valorParcela) {

        CartaoDeCredito cartao = buscarCartaoPorId(cartaoId);

        if (valorParcela <= 0) {
            throw new IllegalArgumentException("O valor da parcela deve ser maior que zero.");
        }

        if (cartao.getGastoAtual() >= valorParcela) {
            cartao.setGastoAtual(cartao.getGastoAtual() - valorParcela);
            cartaoRepository.save(cartao);
            return true;
        } else {
            throw new RuntimeException("O valor da parcela excede o gasto atual do cartão.");
        }
    }



}
