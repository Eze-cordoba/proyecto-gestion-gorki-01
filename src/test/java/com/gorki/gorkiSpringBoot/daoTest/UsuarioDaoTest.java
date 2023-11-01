package com.gorki.gorkiSpringBoot.daoTest;

import com.gorki.gorkiSpringBoot.dao.UsuarioDao;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static  org.assertj.core.api.Assertions.*;


@DataJpaTest
public class UsuarioDaoTest {
    @Autowired
    UsuarioDao dao;

    Usuario usuarioA;
    Usuario usuarioB;
    Usuario usuarioC;


    @BeforeEach
    void before() {
        /* Inicializar usuarios y guardarlos en la base de datos antes de cada prueba */
        usuarioA = Usuario.builder()
                .username("ez3")
                .password("123")
                .email("ez5smith@hotmail.com")
                .build();
        dao.save(usuarioA);

        usuarioB = Usuario.builder()
                .username("ez3")
                .password("123")
                .email("ez6smith@hotmail.com")
                .build();
        dao.save(usuarioB);

        usuarioC = Usuario.builder()
                .username("ez3")
                .password("123")
                .email("ez7smith@hotmail.com")
                .build();
        dao.save(usuarioC);
    }


    @Test
    void testguardarUsuario() {
        /* Crear y guardar un nuevo usuario en la base de datos */
        Usuario usuario = Usuario.builder()
                .username("ez3")
                .password("123")
                .email("ez3smith@hotmail.com")
                .build();
        Usuario usuarioGuardado = dao.save(usuario);

        /* Verificar que el usuario guardado no sea nulo y que su ID sea mayor que 0 */
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testActualizarUsuario() {
        /* Crear y guardar un nuevo usuario en la base de datos */
        Usuario usuario = Usuario.builder()
                .username("ez3")
                .password("123")
                .email("ez3smith@hotmail.com")
                .build();
        Usuario usuarioBD = dao.save(usuario);

        /* Actualizar el nombre y el correo electrónico del usuario */
        usuarioBD.setUsername("ez4");
        usuarioBD.setEmail("ez4smith@hotmail.com");
        Usuario usuarioActualizado = dao.save(usuarioBD);

        /* Verificar que el nombre y el correo electrónico del usuario guardado coincidan con los datos actualizados */
        assertThat(usuarioActualizado.getUsername()).isEqualTo("ez4");
        assertThat(usuarioActualizado.getEmail()).isEqualTo("ez4smith@hotmail.com");
    }

    @Test
    void testEliminarUsuario() {
        /* Eliminar el usuarioA de la base de datos */
        dao.delete(usuarioA);

        /* Intentar encontrar el usuario eliminado por su ID */
        Optional<Usuario> usuario = dao.findById(usuarioA.getId());

        /* Verificar que el usuario no se encuentre en la base de datos */
        assertThat(usuario).isEmpty();
    }

    @Test
    void testListarEmpleados() {
        /* Obtener una lista de todos los usuarios en la base de datos */
        List<Usuario> usuarios = dao.findAll();

        /* Verificar que la lista no sea nula y que contenga la cantidad esperada de usuarios (3 en este caso) */
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(3);
    }

    @Test
    void testFindById() {
        /* Buscar el usuarioA por su ID en la base de datos */
        Usuario usuarioBd = dao.findById(usuarioA.getId()).get();

        /* Verificar que el usuario encontrado no sea nulo y que su nombre coincida con el nombre original de usuarioA */
        assertThat(usuarioBd).isNotNull();
        assertThat(usuarioBd.getUsername()).isEqualTo("ez3");
    }

}
