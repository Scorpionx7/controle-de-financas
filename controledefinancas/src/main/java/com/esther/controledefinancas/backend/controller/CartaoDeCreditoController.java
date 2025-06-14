package com.esther.controledefinancas.backend.controller;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
import com.esther.controledefinancas.backend.dto.ContaDTO;
import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.service.CartaoDeCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoDeCreditoController {


    @Autowired
    private CartaoDeCreditoService cartaoService;

    // GET: Listar todos os cartões (usando DTO)
    @GetMapping
    public ResponseEntity<List<CartaoDeCreditoDTO>> listarCartoes() {
        List<CartaoDeCreditoDTO> cartoes = cartaoService.listarTodos();
        return ResponseEntity.ok(cartoes);
    }

    // GET: Buscar cartão por ID
    @GetMapping("/{id}")
    public ResponseEntity<CartaoDeCreditoDTO> buscarCartaoPorId(@PathVariable Long id) {
        return cartaoService.findById(id) // Retorna Optional<CartaoDeCredito>
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
                    return ResponseEntity.ok(new CartaoDeCreditoDTO(
                            cartao.getId(),
                            cartao.getNome(),
                            cartao.getLimite(),
                            cartao.getGastoAtual(),
                            contaDTO // Inclui o ContaDTO associado
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Criar um novo cartão
    @PostMapping
    public ResponseEntity<CartaoDeCredito> criarCartao(@RequestBody CartaoDeCredito cartao) {
        try {
            CartaoDeCredito novoCartao = cartaoService.criarCartao(cartao);
            return new ResponseEntity<>(novoCartao, HttpStatus.CREATED); // Retorna 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Em caso de limite <= 0
        }
    }

    // PUT: Atualizar um cartão existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<CartaoDeCredito> atualizarCartao(@PathVariable ("id")Long id, @RequestBody CartaoDeCredito cartao) {
        try {
            // Garante que o ID do corpo da requisição é o mesmo do path
            if (cartao.getId() != null && !cartao.getId().equals(id)) {
                return ResponseEntity.badRequest().body(null); // ID no corpo não corresponde ao path
            }
            cartao.setId(id); // Garante que o ID correto será usado para atualização
            CartaoDeCredito cartaoAtualizado = cartaoService.atualizarCartao(id, cartao);
            return ResponseEntity.ok(cartaoAtualizado);
        } catch (RuntimeException e) {
            // Retorna 404 se o cartão não for encontrado, ou 400 para outros erros do service
            return ResponseEntity.badRequest().body(null);
        }
    }

    // DELETE: Deletar um cartão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCartao(@PathVariable ("id") Long id) {
        try {
            cartaoService.deletarCartao(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (RuntimeException e) {
            // Se o service lançar RuntimeException (por exemplo, "Cartão não encontrado"), retorna 404
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint de quitar parcela (ajustado para ser um PUT ou POST mais específico)
    @PostMapping("/{id}/quitar-parcela") // Melhor usar POST para ação, ou PUT se for um update de recurso
    public ResponseEntity<String> quitarParcela(
            @PathVariable ("id") Long id, // Alterado para 'id' para consistência com o path
            @RequestParam double valorParcela) {
        try {
            cartaoService.quitarParcela(id, valorParcela);
            return ResponseEntity.ok("Parcela quitada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}