package com.gorki.gorkiSpringBoot.serviceTest;

import com.gorki.gorkiSpringBoot.dao.UsuarioDao;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import com.gorki.gorkiSpringBoot.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static  org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    UsuarioDao dao;

    @InjectMocks
    UsuarioServiceImpl service;

    Usuario usuarioA;
    Usuario usuarioB;
    Usuario usuarioC;

    @BeforeEach
    void before() {
        /* Inicializar usuarios y guardarlos en la base de datos antes de cada prueba */
        usuarioA = Usuario.builder()
                .id(1L)
                .firstname("ez5")
                .lastname("smith")
                .email("ez5smith@hotmail.com")
                .build();
        // dao.save(usuarioA);

        usuarioB = Usuario.builder()
                .id(2L)
                .firstname("ez6")
                .lastname("smith")
                .email("ez6smith@hotmail.com")
                .build();
        dao.save(usuarioB);

        usuarioC = Usuario.builder()
                .id(3L)
                .firstname("ez7")
                .lastname("smith")
                .email("ez7smith@hotmail.com")
                .build();
        dao.save(usuarioC);
    }



    @Test
    void TestGuardarUsuario() {

        given(dao.save(usuarioA)).willReturn(usuarioA);

        Usuario usuarioGuardado = service.crearUsuario(usuarioA);

        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getEmail()).isEqualTo("ez5smith@hotmail.com");

    }

    @Test
    void testFindUsuarioById(){

        given(dao.findById(usuarioB.getId()) ).willReturn(Optional.of(usuarioB));

        Usuario usuarioEncontrado = service.findUsuario(usuarioB.getId());

        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getFirstname()).isEqualTo("ez6");


    }

    @Test
    void testFindUsuarios(){

        List<Usuario>usuarios = new ArrayList<>();
        usuarios.add(usuarioB);
        usuarios.add(usuarioC);

        when(dao.findAll()).thenReturn(usuarios);

        List<Usuario>usuariosBd = dao.findAll();

        assert usuarios.equals(usuariosBd);

    }

    @Test
    void testFindUsuariosFailed (){

        when(dao.findAll()).thenThrow(new RuntimeException("Simulación de error en la base de datos"));

        try {
            service.findAll();

        } catch (ResponseStatusException e) {

            assert e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR:"fallo como no se esperaba";

            return;
        }

    }

    @Test
    void testActualizarUsuario(){

        given(dao.save(usuarioC)).willReturn(usuarioC);
        usuarioC.setEmail("ez9smith@gmail.com");
        usuarioC.setFirstname("ez9");

        Usuario usuarioActualizado = service.updateUsuario(usuarioC);

        assertThat(usuarioActualizado.getEmail()).isEqualTo("ez9smith@gmail.com");
        assertThat(usuarioActualizado.getFirstname()).isEqualTo("ez9");

    }

    @Test
    void testActualizarUsuarioFailed(){

        when(dao.save(usuarioA)).thenThrow(new RuntimeException("Simulación de error en la base de datos"));

        try {
            service.updateUsuario(usuarioA);

        } catch (ResponseStatusException e) {

            assert e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR:"fallo como no se esperaba";

            return;
        }

    }



    @Test
    void testEliminarUsuarioById(){

        service.eliminarUsuario(usuarioB.getId());
        verify(dao).deleteById(usuarioB.getId());

    }



}
