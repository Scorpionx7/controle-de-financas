package com.esther.controledefinancas.backend.config;

import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Compra;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.model.enuns.FormaPagamento;
import com.esther.controledefinancas.backend.repository.CartaoDeCreditoRepository;
import com.esther.controledefinancas.backend.repository.CompraRepository;
import com.esther.controledefinancas.backend.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CartaoDeCreditoRepository cartaoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já há dados no banco
        if (contaRepository.count() == 0 && cartaoRepository.count() == 0 && compraRepository.count() == 0) {
            // Criar Conta
            Conta conta = new Conta();
            conta.setSaldo(5000.0);
            conta.setValeAlimentacao(600.0);
            contaRepository.save(conta);

            // Criar Cartão de Crédito
            CartaoDeCredito cartao = new CartaoDeCredito();
            cartao.setNome("Cartão Visa");
            cartao.setLimite(3000.0);
            cartao.setGastoAtual(500.0);
            cartaoRepository.save(cartao);

            // Criar Compras
            Compra compra1 = new Compra();
            compra1.setDescricao("Supermercado");
            compra1.setValor(200.0);
            compra1.setFormaPagamento(FormaPagamento.A_VISTA);
            compra1.setConta(conta);
            compra1.setFinalizada(true);
            compra1.setData(LocalDate.now());
            compraRepository.save(compra1);

            Compra compra2 = new Compra();
            compra2.setDescricao("Loja de Roupas");
            compra2.setValor(300.0);
            compra2.setFormaPagamento(FormaPagamento.CARTAO);
            compra2.setCartao(cartao);
            compra2.setFinalizada(false);
            compra2.setParcelas(3);
            compra2.setParcelasRestantes(2);
            compra2.setData(LocalDate.now().minusDays(5));
            compraRepository.save(compra2);

            System.out.println("Dados de exemplo inseridos com sucesso!");
        }
    }
}