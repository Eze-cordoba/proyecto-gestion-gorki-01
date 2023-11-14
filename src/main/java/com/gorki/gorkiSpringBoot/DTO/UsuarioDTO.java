package com.gorki.gorkiSpringBoot.DTO;

import com.gorki.gorkiSpringBoot.entidades.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {


    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String rol;


    public UsuarioDTO(Usuario usuario){

        this.firstname = usuario.getFirstname();
        this.lastname = usuario.getLastname();
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.rol = usuario.getRole().name();

    }

}
