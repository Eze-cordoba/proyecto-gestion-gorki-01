package com.gorki.gorkiSpringBoot.services;

import com.gorki.gorkiSpringBoot.dao.HorarioDisponibleDao;
import com.gorki.gorkiSpringBoot.entidades.HorarioDisponible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class HorariosDisponiblesServiceImpl {


    @Autowired
    HorarioDisponibleDao dao;

    public List<HorarioDisponible> findAll () {

        try {

            return	dao.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo listar los usuarios");
        }


    }


    public HorarioDisponible findHorarioDisponible(Long id) {

        try {

            return dao.findById(id).orElse(null);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo encontrar al usuario con el id" + id);
        }

    }


    public HorarioDisponible crearHorarioDisponible(HorarioDisponible horarioDisponible) {

        try {
            return dao.save(horarioDisponible);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear al usuario");
        }

    }


    public void eliminarHorarioDisponible (Long id) {

        try {

            dao.deleteById(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo eliminar al usuario con el id"+ id);
        }

    }

}
