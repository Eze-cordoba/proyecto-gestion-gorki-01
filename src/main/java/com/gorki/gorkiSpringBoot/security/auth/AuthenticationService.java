package com.gorki.gorkiSpringBoot.security.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorki.gorkiSpringBoot.dao.UsuarioDao;
import com.gorki.gorkiSpringBoot.entidades.Role;
import com.gorki.gorkiSpringBoot.entidades.Usuario;
import com.gorki.gorkiSpringBoot.security.config.JwtService;
import com.gorki.gorkiSpringBoot.security.token.Token;
import com.gorki.gorkiSpringBoot.security.token.TokenRepository;
import com.gorki.gorkiSpringBoot.security.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
  private final UsuarioDao repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public  AuthenticationResponse register(RegisterRequest request) {
   
	try {
		  var usuario = Usuario.builder()
			        .firstname(request.getFirstname())
			        .lastname(request.getLastname())
			        .email(request.getEmail())
			        .password(passwordEncoder.encode(request.getPassword()))
			        .role(request.getRole())
			        .build();
			   
			    var savedAlumno = repository.save(usuario);
			    var jwtToken = jwtService.generateToken(usuario);
			    var refreshToken = jwtService.generateRefreshToken(usuario);
			    saveUserToken(savedAlumno, jwtToken);
			    
				if (usuario.getRole().equals(Role.DEPORTISTA) ) {
				    return AuthenticationResponse.builder()
					        .accessToken(jwtToken)
					            .refreshToken(refreshToken)
					            .idAlumno(usuario.getId())
					            .rol("DEPORTISTA")
					        .build();
					
				}else {
				    return AuthenticationResponse.builder()
					        .accessToken(jwtToken)
					            .refreshToken(refreshToken)
					            .idAlumno(usuario.getId())
					            .rol("ADMIN")
					        .build();
				}		
				
	 } catch (DataIntegrityViolationException e) {
	        // Error: El correo electrónico ya está en uso
	        return AuthenticationResponse.builder()
	            .error("El correo electrónico ya está en uso")
	            .errorCode(1001)
	            .build();
	    } catch (Exception e) {
	        // Otros errores
	        return AuthenticationResponse.builder()
	            .error("Error al registrar el usuario")
	            .errorCode(500)
	            .build();
	    }
	  
    
  }

  public  AuthenticationResponse authenticate(AuthenticationRequest request) {
	  
	  System.out.println(request.getEmail() + request.getPassword());
    
	  try {
		  authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			            request.getEmail(),
			            request.getPassword() 
			        )
			    );
		  
		  var usuario = repository.findByEmail(request.getEmail())
			        .orElseThrow();
			    var jwtToken = jwtService.generateToken(usuario);
			    var refreshToken = jwtService.generateRefreshToken(usuario);
			    revokeAllUserTokens(usuario);
			    saveUserToken(usuario, jwtToken);


		  if (usuario.getRole().equals(Role.DEPORTISTA) ) {
			  return AuthenticationResponse.builder()
					  .accessToken(jwtToken)
					  .refreshToken(refreshToken)
					  .idAlumno(usuario.getId())
					  .rol("DEPORTISTA")
					  .build();

		  }else {
			  return AuthenticationResponse.builder()
					  .accessToken(jwtToken)
					  .refreshToken(refreshToken)
					  .idAlumno(usuario.getId())
					  .rol("ADMIN")
					  .build();
		  }

	  } catch (Exception e) {
		
	  System.out.println(e.getMessage());
		
    return null;
		
	}
	
  }
  
  
  public   AuthenticationResponse editarUsuario(Long alumnoId, UpdateRequest request) {
   
	  Usuario usuario = repository.findById(alumnoId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
      
      // Actualizar los campos necesarios del alumno con los valores proporcionados en la solicitud
      usuario.setFirstname(request.getFirstname());
      usuario.setLastname(request.getLastname());
      usuario.setEmail(request.getEmail());
      
      // Actualizar el password si se proporcionó un nuevo valor
      if (request.getPassword() != null) {
          String encryptedPassword = passwordEncoder.encode(request.getPassword());
          usuario.setPassword(encryptedPassword);
      }
      
      // Guardar el alumno actualizado en la base de datos
      Usuario savedAlumno = repository.save(usuario);
      
      // Generar el token y el refresh token actualizados
      String jwtToken = jwtService.generateToken(savedAlumno);
      String refreshToken = jwtService.generateRefreshToken(savedAlumno);
      
      // Guardar el token actualizado en la base de datos
     UpdateUserToken(savedAlumno, jwtToken);
      
      // Construir y devolver la respuesta de autenticación con los tokens actualizados y otros datos relevantes

	  if (usuario.getRole().equals(Role.DEPORTISTA) ) {
		  return AuthenticationResponse.builder()
				  .accessToken(jwtToken)
				  .refreshToken(refreshToken)
				  .idAlumno(usuario.getId())
				  .rol("DEPORTISTA")
				  .build();

	  }else {
		  return AuthenticationResponse.builder()
				  .accessToken(jwtToken)
				  .refreshToken(refreshToken)
				  .idAlumno(usuario.getId())
				  .rol("ADMIN")
				  .build();
	  }


  }

  
  private void saveUserToken(Usuario alumno, String jwtToken) {
    var token = Token.builder()
        .usuario(alumno)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build(); 
    tokenRepository.save(token);
   
  }

  private void UpdateUserToken(Usuario usuario, String jwtToken) {
	    Token token = tokenRepository.findByUsuario(usuario);
	    if (token != null) {
	        token.setToken(jwtToken);
	        tokenRepository.save(token);
	    } else {
	        token = Token.builder()
	            .usuario(usuario)
	            .token(jwtToken)
	            .tokenType(TokenType.BEARER)
	            .expired(false)
	            .revoked(false)
	            .build(); 
	        tokenRepository.save(token);
	    }
	}
  
  
  private void revokeAllUserTokens(Usuario usuario) {
    var validUserTokens = tokenRepository.findAllValidTokenByAlumno(usuario.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
