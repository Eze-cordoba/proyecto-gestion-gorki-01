package com.gorki.gorkiSpringBoot;

import com.gorki.gorkiSpringBoot.entidades.DeporteReservable;
import com.gorki.gorkiSpringBoot.entidades.HorarioDisponible;

import com.gorki.gorkiSpringBoot.services.DeporteReservableServiceImpl;
import com.gorki.gorkiSpringBoot.services.HorariosDisponiblesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class GorkiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GorkiSpringBootApplication.class, args);

	}

/*
	@Bean
	public CommandLineRunner commandLineRunner(HorariosDisponiblesServiceImpl service, DeporteReservableServiceImpl deporteReservableService) {
		return args -> {

			DeporteReservable padel = new DeporteReservable();
			padel.setNombre("padel");
		  DeporteReservable padelDb = deporteReservableService.crearDeporteReservable(padel);
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
					HorarioDisponible disponible = service.crearHorarioDisponible(reserva);
					disponible.setDeporteReservable(padelDb);
					padelDb.getHorariosDisponibles().add(disponible);

					// Siguiente reserva comienza 2 horas después
					inicio = inicio.plusHours(2);
				}

			}

			deporteReservableService.updateDeporteReservable(padelDb);

			System.out.println("holaaaa");


		};

	}*/




}
