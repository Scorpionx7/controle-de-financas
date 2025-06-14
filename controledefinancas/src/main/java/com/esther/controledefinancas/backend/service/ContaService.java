package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    // Novo metodo: Atualizar Conta
    @Transactional // Garante que a operação seja atômica
    public Conta atualizarConta(Long id, Conta contaAtualizada) {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + id));

        // Atualiza apenas os campos que podem ser modificados
        contaExistente.setNome(contaAtualizada.getNome());
        contaExistente.setSaldo(contaAtualizada.getSaldo());
        contaExistente.setLimite(contaAtualizada.getLimite());
        contaExistente.setValeAlimentacao(contaAtualizada.getValeAlimentacao());

        return contaRepository.save(contaExistente);
    }

    // Novo metodo: Deletar Conta
    public void deletarConta(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new RuntimeException("Conta não encontrada com ID: " + id);
        }
        // Antes de deletar, você pode querer verificar se há cartões ou compras associadas
        // ou configurar o CascadeType.ALL no relacionamento para deletar em cascata.
        // Atualmente, Conta tem CascadeType.ALL para cartoes, o que significa que cartoes associados serão deletados.
        contaRepository.deleteById(id);
    }

    // Para facilitar a busca de uma conta específica para o DTO (se necessário)
    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }


}
