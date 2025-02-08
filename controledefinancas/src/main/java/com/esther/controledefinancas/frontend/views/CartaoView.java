package com.esther.controledefinancas.frontend.views;

import com.esther.controledefinancas.backend.model.CartaoDeCredito;
import com.esther.controledefinancas.backend.model.Conta;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        Label lblNome = new Label("Card Name:");
        TextField txtNome = new TextField();

        Label lblLimite = new Label("Bound:");
        TextField txtLimite = new TextField();

        Label lblConta = new Label("Associate Account:");
        ComboBox<Conta> cmbConta = new ComboBox<>();
        carregarContas(cmbConta); // Método para carregar contas do backend.


        Button btnSalvar = new Button("Save");

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String limite = txtLimite.getText();
            Conta contaSelecionada = cmbConta.getValue(); // Obtém a conta selecionada

            if (nome.isEmpty() || limite.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all fields.");
                alert.showAndWait();
                return;
            }

            try {
                double limiteValor = Double.parseDouble(limite);
                CartaoDeCredito cartao = new CartaoDeCredito();
                cartao.setNome(nome);
                cartao.setLimite(limiteValor);

                // Envia o cartão para o backend
                enviarCartao(cartao);

                // Verifica se há uma conta selecionada para vinculação
                if (contaSelecionada != null) {
                    vincularCartao(contaSelecionada.getId(), cartao.getId());
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Card registered successfully!");
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The limit must be a valid number.");
                alert.showAndWait();
            }
        });


        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblLimite, 0, 1);
        grid.add(txtLimite, 1, 1);
        grid.add(lblConta, 0, 2); // Adiciona o rótulo "Associar Conta"
        grid.add(cmbConta, 1, 2); // Adiciona o ComboBox para seleção de conta
        grid.add(btnSalvar, 1, 3); //

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Card Registration");
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.show();



    }

    private void enviarCartao(CartaoDeCredito cartao) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/cartoes";
        try {
            restTemplate.postForObject(url, cartao, CartaoDeCredito.class);
            System.out.println("Card sent successfully: " + cartao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void carregarContas(ComboBox<Conta> cmbConta) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contas"; // Endpoint do backend para buscar contas
        try {
            Conta[] contas = restTemplate.getForObject(url, Conta[].class);
            cmbConta.getItems().addAll(contas); // Adiciona as contas ao ComboBox
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading accounts.");
            alert.showAndWait();
        }
    }
    private void vincularCartao(Long contaId, Long cartaoId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contas/" + contaId + "/cartoes/" + cartaoId; // Endpoint do backend para vinculação
        try {
            restTemplate.postForObject(url, null, Void.class); // Faz a vinculação
            System.out.println("Card successfully linked to the account!");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error linking card to account.");
            alert.showAndWait();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
