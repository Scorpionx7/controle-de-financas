package com.esther.controledefinancas.frontend.controllers;

import com.esther.controledefinancas.backend.model.Conta;
import com.esther.controledefinancas.frontend.views.CartaoView;
import com.esther.controledefinancas.frontend.views.ContaView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainController {

    @FXML
    public void abrirTelaConta(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Conta");
            ContaView contaView = new ContaView();
            contaView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTelaCartao(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Cartão");
            CartaoView cadastrarCartaoView = new CartaoView();
            cadastrarCartaoView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirCompras() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esther/controledefinancas/frontend/views/gerenciar_compras.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gerenciar Compras");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirRelatorios() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Relatórios");
        alert.setHeaderText(null);
        alert.setContentText("Abrindo o gerenciamento de relatórios...");
        alert.showAndWait();
    }

    // Exemplo de busca de contas no JavaFX
    public List<Conta> buscarContas() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Conta[]> response = restTemplate.getForEntity("http://localhost:8080/contas", Conta[].class);
        return Arrays.asList(response.getBody());
    }

}
