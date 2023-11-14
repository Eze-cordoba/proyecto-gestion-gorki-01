package com.gorki.gorkiSpringBoot.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {


	  private String firstname;

	  private String lastname;

	  private String email;

	  private String password;
	

	
	}
	