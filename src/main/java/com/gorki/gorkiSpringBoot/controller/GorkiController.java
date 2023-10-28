package com.gorki.gorkiSpringBoot.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GorkiController {


//Esta funcion va a hacer para buscar reservas que ya hayan expirado y eliminarlas
    @Scheduled(fixedRate = 3000) // Ejecutar cada minuto (en milisegundos)
    public void miFuncionProgramada() {
        // Coloca aquí el código que deseas ejecutar cada minuto
        System.out.println("La función se ejecuta cada minuto en el controlador.");
    }



}
