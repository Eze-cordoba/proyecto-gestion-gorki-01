package com.gorki.gorkiSpringBoot.dao;

import com.gorki.gorkiSpringBoot.entidades.Reserva;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaDao  extends JpaRepository<Reserva,Long> {
}
