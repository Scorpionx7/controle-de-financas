package com.esther.controledefinancas.backend.controller;

import com.esther.controledefinancas.backend.dto.CartaoDeCreditoDTO;
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
    public ResponseEntity<CartaoDeCredito> buscarCartaoPorId(@PathVariable Long id) {
        // Usa o findById do service que retorna Optional
        Optional<CartaoDeCredito> cartao = cartaoService.findById(id);
        return cartao.map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com o cartão
                .orElse(ResponseEntity.notFound().build()); // Se não encontrar, retorna 404 Not Found
    }

    // POST: Criar um novo cartão
    // Agora usa o metodo 'criarCartao' do service, que inicializa gastoAtual
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
    public ResponseEntity<CartaoDeCredito> atualizarCartao(@PathVariable Long id, @RequestBody CartaoDeCredito cartao) {
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
    public ResponseEntity<Void> deletarCartao(@PathVariable Long id) {
        try {
            cartaoService.deletarCartao(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (RuntimeException e) {
            // Se o service lançar RuntimeException (por exemplo, "Cartão não encontrado"), retorna 404
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para vincular cartão à conta (mantido da ContaController, mas aqui para referência)
    // Este endpoint geralmente estaria no ContaController
    // @PostMapping("/vincular/{contaId}/{cartaoId}")
    // public ResponseEntity<String> vincularCartaoAConta(@PathVariable Long contaId, @PathVariable Long cartaoId) {
    //     contaService.vincularCartao(contaId, cartaoId);
    //     return ResponseEntity.ok("Cartão vinculado à conta com sucesso!");
    // }

    // Endpoint de quitar parcela (ajustado para ser um PUT ou POST mais específico)
    @PostMapping("/{id}/quitar-parcela") // Melhor usar POST para ação, ou PUT se for um update de recurso
    public ResponseEntity<String> quitarParcela(
            @PathVariable Long id, // Alterado para 'id' para consistência com o path
            @RequestParam double valorParcela) {
        try {
            cartaoService.quitarParcela(id, valorParcela);
            return ResponseEntity.ok("Parcela quitada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}