package com.esther.controledefinancas.frontend;

import com.esther.controledefinancas.frontend.utils.SpringContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleDeFinancasFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa o contexto do Spring
        SpringContext.initialize();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/main.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Controle de Finanças");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); // Inicializa JavaFX
    }
}
