package com.esther.controledefinancas.frontend.views;

import com.esther.controledefinancas.backend.model.Conta;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

public class ContaView extends Application {

    @Override
    public void start(Stage stage) {
        // Layout principal
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Campos do formulário
        Label lblNome = new Label("Nome da Conta:");
        TextField txtNome = new TextField();

        Label lblSaldo = new Label("Saldo Disponível:");
        TextField txtSaldo = new TextField();

        Label lblValeAlimentacao = new Label("Vale Alimentação:");
        TextField txtValeAlimentacao = new TextField();

        Button btnSalvar = new Button("Salvar");

        // Ação do botão salvar
        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String saldo = txtSaldo.getText();
            String valeAlimentacao = txtValeAlimentacao.getText();

            // Validações básicas
            if (nome.isEmpty() || saldo.isEmpty() || valeAlimentacao.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos obrigatórios.");
                alert.showAndWait();
                return;
            }

            // Criação do objeto Conta
            Conta conta = new Conta();
            conta.setNome(nome);
            conta.setSaldo(Double.parseDouble(saldo));
            conta.setValeAlimentacao(Double.parseDouble(valeAlimentacao));

            // Envia para o backend
            enviarConta(conta);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Conta salva com sucesso!");
            alert.showAndWait();
        });

        // Adiciona os campos no layout
        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);

        grid.add(lblSaldo, 0, 1);
        grid.add(txtSaldo, 1, 1);

        grid.add(lblValeAlimentacao, 0, 2);
        grid.add(txtValeAlimentacao, 1, 2);

        grid.add(btnSalvar, 1, 3);

        // Configuração da janela
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Cadastrar Conta");
        stage.show();
    }

    private void enviarConta(Conta conta) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contas";
        try {
            restTemplate.postForObject(url, conta, Conta.class);
            System.out.println("Conta enviada com sucesso: " + conta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
