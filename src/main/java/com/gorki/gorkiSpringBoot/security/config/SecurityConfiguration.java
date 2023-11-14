package com.gorki.gorkiSpringBoot.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.gorki.gorkiSpringBoot.entidades.Role.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration{

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
                "/api/v1/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html"
            //    "/utn/alumnos/{id}",
              //  "/utn/agregarMateria/{idAlumno}"
                    
        )
          .permitAll()
          

            .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
            .requestMatchers("/gorki/obtenerDeportes").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/crearDeporte").hasRole(ADMIN.name())
            .requestMatchers("/gorki/obtenerDeporte/{idDeporte}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/verUsuario/{idUsuario}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/eliminarUsuario/{idUsuario}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/mostrarReservas/{idDeporte}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/hacerReserva/{idUsuario}/{idDeporte}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/eliminarCuenta/{idUsuario}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/cerrarSesion").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/eliminarReservaUsuario/{idReserva}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())
            .requestMatchers("/gorki/mostrarReservasDeUsuario/{idUsuario}").hasAnyRole(ADMIN.name(), DEPORTISTA.name())


            /*

       .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
       .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
       .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
       .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())


       .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

       .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
       .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
       .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
       .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/


        .anyRequest()
          .authenticated()
        .and()
        
        .cors().and()
        
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/v1/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
  
  /* ESTO LO VOY A NECESITAR CUANDO NECESITE una configuración más personalizada para el CORS, como permitir solo ciertos métodos HTTP o encabezados personalizados, 
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }*/

  
  
}
