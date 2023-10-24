package com.gorki.gorkiSpringBoot.entidades;

import java.util.List;

public class Deporte {

    private Long id;

    private String nombre;
    private String descripcion ;
    private String tipo ;

  //si es reservable tiene una variable de tipo list <reservas>
    List<Disponibilidad> reservasDisponibles;
   //list de reservasya echas
    List<Reserva> reservasEchas;
    //si es unirse al grupo tendra un list de usuarios que estan unidos ;
     List <Usuario> usuarios;


}
