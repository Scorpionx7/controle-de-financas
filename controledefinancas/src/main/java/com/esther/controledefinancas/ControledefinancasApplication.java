package com.esther.controledefinancas;

import com.esther.controledefinancas.frontend.views.MainWindow;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControledefinancasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControledefinancasApplication.class, args);

		Application.launch(MainWindow.class, args);
	}

}
