package com.gorki.gorkiSpringBoot.daoTest;

import com.gorki.gorkiSpringBoot.dao.DeporteReservableDao;
import com.gorki.gorkiSpringBoot.dao.UsuarioDao;
import com.gorki.gorkiSpringBoot.entidades.DeporteReservable;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DeporteReservableTest {

    @Autowired
    DeporteReservableDao dao;

    DeporteReservable deporteA;
    DeporteReservable deporteB;
    DeporteReservable deporteC;


    @BeforeEach
    void before() {


        deporteB =  DeporteReservable.builder()
                .nombre("futbol")
                .build();
        dao.save(deporteB);

        deporteC = DeporteReservable.builder()
                .nombre("tenis")
                .build();;
        dao.save(deporteC);
    }


    @Test
    void testguardarDeporteReservable() {
        /* Crear y guardar un nuevo deporte en la base de datos */
        DeporteReservable deporteA = DeporteReservable.builder()
                .nombre("padel")
                .build();
        DeporteReservable deporteBd = dao.save(deporteA);

        /* Verificar que el deporte guardado no sea nulo y que su ID sea mayor que 0 */
        assertThat(deporteBd).isNotNull();
        assertThat(deporteBd.getId()).isGreaterThan(0);
    }

    @Test
    void testActualizarDeporteReservable() {
        /* Crear y guardar un nuevo usuario en la base de datos */
        DeporteReservable deporteA = DeporteReservable.builder()
                .nombre("padel")
                .build();
        DeporteReservable deporteBd = dao.save(deporteA);


        /* Actualizar el nombre y el correo electrónico del usuario */
        deporteBd.setNombre("basquet");
        DeporteReservable deporteBdActualizado = dao.save(deporteBd);

        /* Verificar que el nombre y el correo electrónico del usuario guardado coincidan con los datos actualizados */
        assertThat(deporteBdActualizado.getNombre()).isEqualTo("basquet");

    }

    @Test
    void testEliminarDeporteReservable() {
        /* Eliminar el usuarioA de la base de datos */
        dao.delete(deporteB);

        /* Intentar encontrar el usuario eliminado por su ID */
        Optional<DeporteReservable> deporteBd = dao.findById(deporteB.getId());

        /* Verificar que el usuario no se encuentre en la base de datos */
        assertThat(deporteBd).isEmpty();
    }

    @Test
    void testListarDeporteReservables() {
        /* Obtener una lista de todos los usuarios en la base de datos */
        List<DeporteReservable> deportes = dao.findAll();

        /* Verificar que la lista no sea nula y que contenga la cantidad esperada de usuarios (3 en este caso) */
        assertThat(deportes).isNotNull();
        assertThat(deportes.size()).isEqualTo(2);
    }

    @Test
    void testFindById() {
        /* Buscar el usuarioA por su ID en la base de datos */
        DeporteReservable deporteBd = dao.findById(deporteB.getId()).get();

        /* Verificar que el usuario encontrado no sea nulo y que su nombre coincida con el nombre original de usuarioA */
        assertThat(deporteBd).isNotNull();
        assertThat(deporteBd.getNombre()).isEqualTo("futbol");
    }





}
