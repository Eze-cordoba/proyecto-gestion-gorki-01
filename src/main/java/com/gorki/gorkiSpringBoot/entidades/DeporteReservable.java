package com.gorki.gorkiSpringBoot.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeporteReservable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "deporteReservable", cascade = CascadeType.ALL)
    private List<HorarioDisponible> horariosDisponibles = new ArrayList<>();

    @OneToMany(mappedBy = "deporte",cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();


}
