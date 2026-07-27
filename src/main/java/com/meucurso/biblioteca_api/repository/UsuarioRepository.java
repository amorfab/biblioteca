package com.meucurso.biblioteca_api.repository;

import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    // Spring Data gera o SQL automaticamente: SELECT * FROM usuarios WHERE email = ?
}