package com.gorki.gorkiSpringBoot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorki.gorkiSpringBoot.entidades.DeporteReservable;
import com.gorki.gorkiSpringBoot.entidades.HorarioDisponible;
import com.gorki.gorkiSpringBoot.entidades.Reserva;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import com.gorki.gorkiSpringBoot.services.DeporteReservableServiceImpl;
import com.gorki.gorkiSpringBoot.services.HorariosDisponiblesServiceImpl;
import com.gorki.gorkiSpringBoot.services.ReservaServiceImpl;
import com.gorki.gorkiSpringBoot.services.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/gorki")
@RequiredArgsConstructor
public class GorkiController {

    @Autowired
    DeporteReservableServiceImpl deporteReservableService;
    @Autowired
    UsuarioServiceImpl usuarioService;
    @Autowired
    HorariosDisponiblesServiceImpl horariosDisponiblesService;
    @Autowired
    ReservaServiceImpl reservaService;


//Esta funcion va a hacer para buscar reservas que ya hayan expirado y eliminarlas
    //@Scheduled(fixedRate = 3000) // Ejecutar cada minuto (en milisegundos)
    //public void miFuncionProgramada() {
    // Coloca aquí el código que deseas ejecutar cada minuto
    //    System.out.println("La función se ejecuta cada minuto en el controlador.");
    //}


    @GetMapping("/obtenerDeporte")
    public ResponseEntity<?> obtenerDeportes() {

        return ResponseEntity.ok(deporteReservableService.findAll());

    }


    @PostMapping("/crearDeporte")
    public ResponseEntity<?> crearDeporte(@RequestBody DeporteReservable deporteRequest) {

        DeporteReservable deporte = new DeporteReservable();
        deporte.setNombre(deporte.getNombre());
        deporte.setDescripcion(deporteRequest.getDescripcion());

        DeporteReservable deporteDb = deporteReservableService.crearDeporteReservable(deporte);
        List<HorarioDisponible> horarioDisponibles = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        // Si hoy es sábado (6) o domingo (7), ajusta la fecha al próximo lunes (1)
        if (now.getDayOfWeek().getValue() >= 6) {
            now = now.plusDays(8 - now.getDayOfWeek().getValue());
        }

        // Define el horario de inicio (10:00 AM) y fin (8:00 PM)
        LocalDateTime inicio = LocalDateTime.of(now.toLocalDate(), LocalTime.of(10, 0));
        LocalDateTime fin = LocalDateTime.of(now.toLocalDate(), LocalTime.of(20, 0));
        LocalDateTime nextSaturday = now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        //ACA VOY A AGREGAR UN IF DE SI SE PASO DE LAS 8 DE LA NOCHE  SUMARLE 12 HORAS Y QUE SIGA CREANDO HORARIOS Y SI
        while (inicio.isBefore(nextSaturday)) {
            // Agrega horarios disponibles de lunes a viernes
            if (inicio.toLocalTime().isAfter(LocalTime.of(20, 0))) {
                inicio = inicio.plusHours(12); // Si es después de las 20:00, suma 12 horas
            } else {
                HorarioDisponible reserva = new HorarioDisponible(inicio, inicio.plusHours(2));
                HorarioDisponible disponible = horariosDisponiblesService.crearHorarioDisponible(reserva);
                disponible.setDeporteReservable(deporteDb);
                deporteDb.getHorariosDisponibles().add(disponible);

                // Siguiente reserva comienza 2 horas después
                inicio = inicio.plusHours(2);
            }

        }

        deporteReservableService.updateDeporteReservable(deporteDb);

        System.out.println("deporteCreado");

        return ResponseEntity.ok("afs");
    }


    @GetMapping("/obtenerDeporte/{idDeporte}")
    public ResponseEntity<?> obtenerDeporte(@PathVariable Long idDeporte) {


        return ResponseEntity.ok(deporteReservableService.findDeporteReservableById(idDeporte));

    }


    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {

        Usuario usuarioBd = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(usuarioBd);

    }

    @GetMapping("/verUsuario/{idUsuario}")
    public ResponseEntity<?> verUsuario(@PathVariable Long idUsuario) {

        Usuario usuarioBd = usuarioService.findUsuario(idUsuario);
        return ResponseEntity.ok(usuarioBd);

    }

    @DeleteMapping("/eliminarUsuario/{idUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long idUsuario) {

        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok("todoBien");

    }


    @GetMapping("/mostrarReservas/{idDeporte}")
    public ResponseEntity<?> mostrarhorariosDisponibles(@PathVariable long idDeporte) {

        DeporteReservable deporte = deporteReservableService.findDeporteReservableById(idDeporte);
        List<HorarioDisponible> horarioDisponibles = deporte.getHorariosDisponibles();

        return ResponseEntity.ok(horarioDisponibles);
    }

    @PutMapping("/hacerReserva/{idUsuario}/{idDeporte}")
    public ResponseEntity<?> hacerReserva(@PathVariable Long idUsuario, @PathVariable Long idDeporte, @RequestBody Map<String, String> requestBody) {

        try {

            Usuario usuarioBd = usuarioService.findUsuario(idUsuario);
            DeporteReservable deporteBd = deporteReservableService.findDeporteReservableById(idDeporte);

            // Obtiene el valor del campo "fecha" del objeto JSON
            String fecha = requestBody.get("fecha");

            // Especifica el formato de la cadena de fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime horario = LocalDateTime.parse(fecha, formatter);

            LocalDateTime ahora = LocalDateTime.now();
            try {

                if (ahora.isBefore(horario)) {

                    System.out.println("PODES RESERVAR TRANQUILO");

                    for (Reserva reserva : deporteBd.getReservas()) {

                        if (reserva.getFechaInicio().equals(horario)) {
                            return ResponseEntity.ok("ESTE HORARIO YA ESTA RESERVADO");
                        }

                    }

                    for (HorarioDisponible horarioDisponible : deporteBd.getHorariosDisponibles()) {
                        if (horarioDisponible.getFechaInicio().equals(horario)) {

                            Reserva nuevaReserva = new Reserva();
                            nuevaReserva.setFechaInicio(horarioDisponible.getFechaInicio());
                            nuevaReserva.setFechaFin(horarioDisponible.getFechaFin());
                            nuevaReserva.setDeporte(deporteBd);
                            nuevaReserva.setUsuario(usuarioBd);
                            reservaService.crearReserva(nuevaReserva);
                        }
                    }

                } else {
                    return ResponseEntity.ok("EL HORARIO YA EXPIRO");
                }

            } catch (Exception e) {
                System.out.println(e.getCause() + e.getMessage());
            }

            return ResponseEntity.ok("Fecha procesada exitosamente");


        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
        }

        return ResponseEntity.ok("Fecha procesada exitosamente");

    }


}
