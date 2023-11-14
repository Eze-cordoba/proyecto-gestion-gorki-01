package com.gorki.gorkiSpringBoot.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  
  @JsonProperty("id_alumno")
  private Long idAlumno;
  
  @JsonProperty("rol_usuario")
  private String rol ;
  
  // Propiedades para manejar errores
  @JsonProperty("error")
  private String error;
  @JsonProperty("error_code")
  private int errorCode;

  
}
