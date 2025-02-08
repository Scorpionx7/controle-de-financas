package com.esther.controledefinancas.frontend.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RelatoriosView extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Generate Reports");

        Label lblDataInicio = new Label("Start Date:");
        DatePicker dpDataInicio = new DatePicker();

        Label lblDataFim = new Label("End Date:");
        DatePicker dpDataFim = new DatePicker();

        Button btnGerar = new Button("Generate Reports");
        btnGerar.setOnAction(event -> {
            // Implementar lógica para gerar relatório e salvar em PDF
            System.out.println("Report generated from " + dpDataInicio.getValue() + " to " + dpDataFim.getValue());
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(lblDataInicio, dpDataInicio, lblDataFim, dpDataFim, btnGerar);

        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
