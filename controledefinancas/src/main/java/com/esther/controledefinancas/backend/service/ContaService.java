package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private CartaoDeCreditoRepository cartaoRepository;

    public Conta criarConta(Conta conta) {
        if (conta.getNome() == null) throw new NullPointerException("Conta não pode ser nula!");
        return contaRepository.save(conta);
    }

    public double consultarSaldo(Long contaId) {
        return contaRepository.findById(contaId).orElseThrow().getSaldo();
    }

    public Conta buscarContaPorId(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + contaId));
    }

    public void atualizarSaldo(Long contaId, double valor) {
        Conta conta = buscarContaPorId(contaId);
        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);
    }

    public List<ContaDTO> listarTodas() {
        return contaRepository.findAll().stream()
                .map(conta -> new ContaDTO(conta.getId(),conta.getNome(), conta.getSaldo(), conta.getLimite(), conta.getValeAlimentacao()))
                .collect(Collectors.toList());
    }

    public void vincularCartao(Long contaId, Long cartaoId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        CartaoDeCredito cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        cartao.setConta(conta);
        cartaoRepository.save(cartao);
    }


}
