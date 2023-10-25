package com.gorki.gorkiSpringBoot.services;

import com.gorki.gorkiSpringBoot.dao.UsuarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl {

    @Autowired
    UsuarioDao dao;

}
