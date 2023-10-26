package com.gorki.gorkiSpringBoot.services;

import com.gorki.gorkiSpringBoot.dao.DeporteReservableDao;
import com.gorki.gorkiSpringBoot.entidades.DeporteReservable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class DeporteReservableServiceImpl {

    DeporteReservableDao dao;

    public List<DeporteReservable> findAll () {

        try {

            return	dao.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo listar los usuarios");
        }


    }


    public DeporteReservable findDeporteReservable(Long id) {

        try {

            return dao.findById(id).orElse(null);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo encontrar al usuario con el id" + id);
        }

    }


    public DeporteReservable crearDeporteReservable(DeporteReservable deporteReservable) {

        try {
            return dao.save(deporteReservable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear al usuario");
        }

    }


    public void eliminarDeporteReservable (Long id) {

        try {

            dao.deleteById(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo eliminar al usuario con el id"+ id);
        }

    }


    public DeporteReservable updateDeporteReservable(DeporteReservable deporteReservable) {

        try {

            return dao.save(deporteReservable);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo actualizar al usuario");
        }

    }


}
