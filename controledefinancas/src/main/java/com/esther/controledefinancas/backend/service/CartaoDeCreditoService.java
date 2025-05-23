package com.esther.controledefinancas.backend.service;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartaoDeCreditoService {

    @Autowired
    private CartaoDeCreditoRepository cartaoRepository;
    @Autowired
    private ContaRepository contaRepository;

    // 1. Criar um NOVO Cartão
    // Este metodo é para quando você está criando um cartão pela primeira vez.
    @Transactional
    public CartaoDeCredito criarCartao(CartaoDeCredito cartao) {
        if (cartao.getLimite() <= 0) {
            throw new IllegalArgumentException("O limite do cartão deve ser maior que zero.");
        }
        cartao.setGastoAtual(0.0); // Gasto inicial é zero para um novo cartão
        return cartaoRepository.save(cartao);
    }

    // 2. Buscar Cartão por ID (retorna Optional para flexibilidade)
    public Optional<CartaoDeCredito> findById(Long id) {
        return cartaoRepository.findById(id);
    }

    // 3. Buscar Cartão por ID (retorna o objeto ou lança exceção, útil para validações internas)
    public CartaoDeCredito getCartaoById(Long id) {
        return cartaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Cartão não encontrado com ID: " + id));
    }

    // 4. Registrar uma Compra no Cartão (lógica existente e necessária)
    @Transactional
    public boolean registrarCompra(Long cartaoId, double valor) {
        CartaoDeCredito cartao = getCartaoById(cartaoId); // Usa o getCartaoById para buscar
        if (cartao.registrarCompra(valor)) { // Lógica de limite já está no modelo
            cartaoRepository.save(cartao);
            return true;
        }
        throw new RuntimeException("Limite insuficiente no cartão.");
    }

    // 5. Listar TODOS os Cartões (com DTO e Conta associada)
    public List<CartaoDeCreditoDTO> listarTodos() {
        return cartaoRepository.findAll().stream()
                .map(cartao -> {
                    ContaDTO contaDTO = null;
                    if (cartao.getConta() != null) {
                        contaDTO = new ContaDTO(
                                cartao.getConta().getId(),
                                cartao.getConta().getNome(),
                                cartao.getConta().getSaldo(),
                                cartao.getConta().getLimite(),
                                cartao.getConta().getValeAlimentacao()
                        );
                    }
                    return new CartaoDeCreditoDTO(
                            cartao.getId(),
                            cartao.getNome(),
                            cartao.getLimite(),
                            cartao.getGastoAtual(),
                            contaDTO
                    );
                })
                .collect(Collectors.toList());
    }

    // 6. Quitar Parcela (lógica existente e necessária)
    @Transactional
    public boolean quitarParcela(Long cartaoId, double valorParcela) {
        CartaoDeCredito cartao = getCartaoById(cartaoId); // Usa o getCartaoById para buscar

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

    // 7. Atualizar um Cartão Existente
    // Este metodo é para quando você já tem o ID e quer atualizar os dados do cartão.
    @Transactional
    public CartaoDeCredito atualizarCartao(Long id, CartaoDeCredito cartaoAtualizado) {
        CartaoDeCredito cartaoExistente = getCartaoById(id); // Usa o getCartaoById

        // Atualiza apenas os campos que podem ser modificados pelo usuário no formulário de edição
        cartaoExistente.setNome(cartaoAtualizado.getNome());
        cartaoExistente.setLimite(cartaoAtualizado.getLimite());
        // Não atualizamos gastoAtual aqui, pois ele é controlado por compras/quitação

        // Se você quiser permitir a mudança da conta associada via este método,
        // precisaria buscar a nova Conta por ID e setá-la no cartaoExistente.
        // Por enquanto, a vinculação é feita separadamente pelo endpoint de contas.

        return cartaoRepository.save(cartaoExistente);
    }

    // 8. Deletar um Cartão
    @Transactional
    public void deletarCartao(Long id) {
        if (!cartaoRepository.existsById(id)) {
            throw new RuntimeException("Cartão não encontrado com ID: " + id);
        }
        // Considere a política de exclusão de compras associadas se necessário.
        // Se as compras tiverem CascadeType.ALL com o cartão, elas serão deletadas em cascata.
        cartaoRepository.deleteById(id);
    }
}