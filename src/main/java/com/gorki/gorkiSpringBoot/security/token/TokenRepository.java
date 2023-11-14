package com.gorki.gorkiSpringBoot.security.token;



import com.gorki.gorkiSpringBoot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join Usuario u\s
      on t.usuario.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByAlumno(Long id);

  Optional<Token> findByToken(String token);
  
 Token  findByUsuario( Usuario usuario);
  
}
