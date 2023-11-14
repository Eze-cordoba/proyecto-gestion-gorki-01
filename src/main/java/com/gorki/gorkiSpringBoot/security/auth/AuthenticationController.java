package com.gorki.gorkiSpringBoot.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

	/**
	 * Registra a un usuario utilizando los datos proporcionados en el objeto RegisterRequest.
	 *
	 * @param request   La solicitud de registro con la información del usuario.
	 * @return          Una respuesta que indica si el registro se realizó con éxito o si hubo algún error.
	 */
  @PostMapping("/register")
  public ResponseEntity<?> register(  @RequestBody RegisterRequest request) {
	  
	  Map<String, Object> response = new HashMap<>();
	AuthenticationResponse authenticationResponse = service.register(request);
	
	if (authenticationResponse.getError() != null ) {
		  response.put("error",authenticationResponse.getError());
		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	} else {
		   return ResponseEntity.ok(authenticationResponse);
	}
	  
  }
  

	/**
	 * Autentica a un usuario utilizando las credenciales proporcionadas en la solicitud.
	 *
	 * @param request La solicitud de autenticación que contiene las credenciales del usuario.
	 * @return La respuesta de autenticación que incluye un token si la autenticación es exitosa.
	 */
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate( @RequestBody AuthenticationRequest request ) {
	  
	  AuthenticationResponse authenticationResponse = service.authenticate(request);
	    
	    if (authenticationResponse == null) {
	        // Autenticación fallida, devuelve una respuesta de error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } else {
	        // Autenticación exitosa, devuelve la respuesta con el token
	        return ResponseEntity.ok(authenticationResponse);
	    }
	    
	}


	/**
	 * Refresca el token de autenticación del usuario.
	 *
	 * @param request  La solicitud HTTP que contiene la información necesaria para el refresco del token.
	 * @param response La respuesta HTTP en la que se enviará el nuevo token de autenticación.
	 * @throws IOException Si ocurre un error al manejar la solicitud o respuesta.
	 */
  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
