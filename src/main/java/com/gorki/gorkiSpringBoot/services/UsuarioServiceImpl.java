package com.gorki.gorkiSpringBoot.services;

import com.gorki.gorkiSpringBoot.dao.UsuarioDao;

import com.gorki.gorkiSpringBoot.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioServiceImpl {

    @Autowired
    UsuarioDao dao;

    public List<Usuario> findAll () {

        try {

            return	dao.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo listar los usuarios error :"+ e.getCause());
        }


    }


    public Usuario findUsuario(Long id) {

        try {

            return dao.findById(id).orElse(null);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo encontrar al usuario con el id" + id+ ", error :"+ e.getCause());
        }

    }


    public Usuario crearUsuario(Usuario usuario) {

        try {
            return dao.save(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear al usuario, error :"+ e.getCause());
        }

    }


    public void eliminarUsuario (Long id) {

        try {

            dao.deleteById(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo eliminar al usuario con el id"+ id +", error :"+ e.getCause());
        }

    }


    public Usuario updateUsuario(Usuario usuario) {

        try {

            return dao.save(usuario);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo actualizar al usuario, error :"+ e.getCause());
        }

    }


}
