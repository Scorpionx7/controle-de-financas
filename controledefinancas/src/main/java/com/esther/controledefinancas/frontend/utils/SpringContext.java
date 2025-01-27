package com.esther.controledefinancas.frontend.utils;

import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {

    @Getter
    private static ApplicationContext context;

    public static void initialize() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(
                    com.esther.controledefinancas.ControledefinancasApplication.class
            );
        }
    }
}
