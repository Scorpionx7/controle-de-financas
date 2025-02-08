package com.esther.controledefinancas.frontend.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SMART BUDGET");

        // Botões para as funcionalidades
        Button btnContas = new Button("Accounts");
        Button btnCartoes = new Button("Card");
        Button btnCompras = new Button("Add Expense");
        Button btnRelatorios = new Button("My Financial Summary");
        Button btnSair = new Button("Exit");

        // Ações dos botões

        btnContas.setOnAction(event -> {
            ContaView contaView = new ContaView();
            try {
                contaView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        btnCompras.setOnAction(event -> {
            ComprasView comprasView = new ComprasView();
            try {
                comprasView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnRelatorios.setOnAction(event -> {
            RelatoriosView relatoriosView = new RelatoriosView();
            try {
                relatoriosView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnCartoes.setOnAction(event -> {
            CartaoView cartaoView = new CartaoView();
            try {
                cartaoView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        btnSair.setOnAction(event -> primaryStage.close());

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(btnContas,btnCartoes,btnCompras, btnRelatorios, btnSair);

        // Configuração da cena
        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
