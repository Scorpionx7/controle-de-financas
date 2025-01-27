package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta criarConta(Conta conta) {
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
                .map(conta -> new ContaDTO(conta.getId(), conta.getSaldo(), conta.getValeAlimentacao()))
                .collect(Collectors.toList());
    }
}
