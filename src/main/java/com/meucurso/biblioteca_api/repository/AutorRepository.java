package com.meucurso.biblioteca_api.repository;

import com.meucurso.biblioteca_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
