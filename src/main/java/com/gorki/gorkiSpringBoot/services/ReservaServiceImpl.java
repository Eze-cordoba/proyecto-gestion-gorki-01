package com.gorki.gorkiSpringBoot.services;

import com.gorki.gorkiSpringBoot.dao.ReservaDao;
import com.gorki.gorkiSpringBoot.entidades.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservaServiceImpl {


    @Autowired
    ReservaDao dao;

    public List<Reserva> findAll () {

        try {

            return	dao.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo listar los usuarios, error :"+ e.getCause());
        }


    }


    public Reserva findReserva(Long id) {

        try {

            return dao.findById(id).orElse(null);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo encontrar al usuario con el id" + id +", error :"+ e.getCause());
        }

    }


    public Reserva crearReserva(Reserva reserva) {

        try {
            return dao.save(reserva);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear al usuario, error :"+ e.getCause());
        }

    }


    public void eliminarReserva (Long id) {

        try {

            dao.deleteById(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo eliminar al usuario con el id"+ id + ", error :"+ e.getCause());
        }

    }

}
