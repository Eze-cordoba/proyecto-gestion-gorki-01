package com.gorki.gorkiSpringBoot.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeporteReservable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  nombre;

    private String descripcion;

    @OneToMany
    private List<HorarioDisponible> horariosDisponibles;

    @OneToMany(mappedBy = "deporte")
    private List<Reserva> reservas;


}
