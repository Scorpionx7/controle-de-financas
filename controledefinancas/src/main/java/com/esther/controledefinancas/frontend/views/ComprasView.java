package com.esther.controledefinancas.frontend.views;

import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Compra;
import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.backend.model.enuns.FormaPagamento;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ComprasView extends Application {

    public void start(Stage stage) {
        // Layout principal
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Campos do formulário
        Label lblDescricao = new Label("Descrição da Compra:");
        TextField txtDescricao = new TextField();

        Label lblValor = new Label("Valor:");
        TextField txtValor = new TextField();

        Label lblFormaPagamento = new Label("Forma de Pagamento:");
        ComboBox<String> cmbFormaPagamento = new ComboBox<>();
        cmbFormaPagamento.getItems().addAll("A_VISTA", "CARTAO", "VALE_ALIMENTACAO");

        Label lblParcelado = new Label("Parcelado:");
        CheckBox chkParcelado = new CheckBox();

        Label lblQtdParcelas = new Label("Quantidade de Parcelas:");
        TextField txtQtdParcelas = new TextField();
        txtQtdParcelas.setDisable(true); // Desativado por padrão

        Label lblConta = new Label("Conta:");
        ComboBox<Conta> cmbConta = new ComboBox<>();

        Label lblCartao = new Label("Cartão:");
        ComboBox<CartaoDeCredito> cmbCartao = new ComboBox<>();

        Label lblLimiteDisponivel = new Label("Limite Disponível: R$ 0.00");

        // Habilita/desabilita o campo de parcelas baseado no checkbox
        chkParcelado.setOnAction(e -> txtQtdParcelas.setDisable(!chkParcelado.isSelected()));

        // Atualiza o limite disponível ao selecionar um cartão
        cmbCartao.setOnAction(e -> {
            CartaoDeCredito cartaoSelecionado = cmbCartao.getSelectionModel().getSelectedItem();
            if (cartaoSelecionado != null) {
                lblLimiteDisponivel.setText("Limite Disponível: R$ " + cartaoSelecionado.getLimiteDisponivel());
            }
        });

        Button btnSalvar = new Button("Salvar");

        // Ação do botão salvar
        btnSalvar.setOnAction(e -> {
            String descricao = txtDescricao.getText();
            String valor = txtValor.getText();
            String formaPagamento = cmbFormaPagamento.getValue();
            boolean parcelado = chkParcelado.isSelected();
            String qtdParcelas = txtQtdParcelas.getText();
            Conta contaSelecionada = cmbConta.getSelectionModel().getSelectedItem();
            CartaoDeCredito cartaoSelecionado = cmbCartao.getSelectionModel().getSelectedItem();

            // Validações básicas
            if (descricao.isEmpty() || valor.isEmpty() || formaPagamento == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos obrigatórios.");
                alert.showAndWait();
                return;
            }

            if (formaPagamento.equals("CARTAO") && cartaoSelecionado == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selecione um cartão.");
                alert.showAndWait();
                return;
            }

            if (formaPagamento.equals("VALE_ALIMENTACAO") && contaSelecionada == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selecione uma conta.");
                alert.showAndWait();
                return;
            }

            if (parcelado && (qtdParcelas.isEmpty() || Integer.parseInt(qtdParcelas) <= 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Insira uma quantidade válida de parcelas.");
                alert.showAndWait();
                return;
            }

            // Criação do objeto Compra
            Compra compra = new Compra();
            compra.setDescricao(descricao);
            compra.setValor(Double.parseDouble(valor));
            compra.setFormaPagamento(FormaPagamento.valueOf(formaPagamento));
            compra.setFinalizada(false);
            compra.setParcelas(parcelado ? Integer.parseInt(qtdParcelas) : 0);
            compra.setParcelasRestantes(parcelado ? Integer.parseInt(qtdParcelas) : 0);

            if (formaPagamento.equals("CARTAO")) {
                compra.setCartao(cartaoSelecionado);
            } else if (formaPagamento.equals("VALE_ALIMENTACAO")) {
                compra.setConta(contaSelecionada);
            }

            // Envia para o backend
            enviarCompra(compra);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Compra salva com sucesso!");
            alert.showAndWait();
        });

        // Adiciona os campos no layout
        grid.add(lblDescricao, 0, 0);
        grid.add(txtDescricao, 1, 0);

        grid.add(lblValor, 0, 1);
        grid.add(txtValor, 1, 1);

        grid.add(lblFormaPagamento, 0, 2);
        grid.add(cmbFormaPagamento, 1, 2);

        grid.add(lblParcelado, 0, 3);
        grid.add(chkParcelado, 1, 3);

        grid.add(lblQtdParcelas, 0, 4);
        grid.add(txtQtdParcelas, 1, 4);

        grid.add(lblConta, 0, 5);
        grid.add(cmbConta, 1, 5);

        grid.add(lblCartao, 0, 6);
        grid.add(cmbCartao, 1, 6);

        grid.add(lblLimiteDisponivel, 1, 7);

        grid.add(btnSalvar, 1, 8);

        // Configuração da janela
        Scene scene = new Scene(grid, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Gerenciar Compras");
        stage.show();

        // Carregar dados do backend
        carregarContas(cmbConta);
        carregarCartoes(cmbCartao);
    }

    private void carregarContas(ComboBox<Conta> cmbConta) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contas";
        try {
            Conta[] contas = restTemplate.getForObject(url, Conta[].class);
            cmbConta.getItems().addAll(contas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarCartoes(ComboBox<CartaoDeCredito> cmbCartao) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/cartoes";
        try {
            CartaoDeCredito[] cartoes = restTemplate.getForObject(url, CartaoDeCredito[].class);
            cmbCartao.getItems().addAll(cartoes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarCompra(Compra compra) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/compras";
        try {
            restTemplate.postForObject(url, compra, Compra.class);
            System.out.println("Compra enviada com sucesso: " + compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
