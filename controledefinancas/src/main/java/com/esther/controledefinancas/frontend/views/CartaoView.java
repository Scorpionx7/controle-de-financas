package com.esther.controledefinancas.frontend.views;

import com.esther.controledefinancas.backend.model.CartaoDeCredito;
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

public class CartaoView extends Application {

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNome = new Label("Nome do Cartão:");
        TextField txtNome = new TextField();

        Label lblLimite = new Label("Limite:");
        TextField txtLimite = new TextField();

        Button btnSalvar = new Button("Salvar");

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String limite = txtLimite.getText();

            if (nome.isEmpty() || limite.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            try {
                double limiteValor = Double.parseDouble(limite);
                CartaoDeCredito cartao = new CartaoDeCredito();
                cartao.setNome(nome);
                cartao.setLimite(limiteValor);
                enviarCartao(cartao);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cartão cadastrado com sucesso!");
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "O limite deve ser um número válido.");
                alert.showAndWait();
            }
        });

        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblLimite, 0, 1);
        grid.add(txtLimite, 1, 1);
        grid.add(btnSalvar, 1, 2);

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Cartões");
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.show();



    }

    private void enviarCartao(CartaoDeCredito cartao) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/cartoes";
        try {
            restTemplate.postForObject(url, cartao, CartaoDeCredito.class);
            System.out.println("Cartão enviado com sucesso: " + cartao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
