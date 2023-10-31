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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username ;
    private String password ;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Reserva> reservas;




}
