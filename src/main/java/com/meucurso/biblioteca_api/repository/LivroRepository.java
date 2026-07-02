package com.meucurso.biblioteca_api.repository;

import com.meucurso.biblioteca_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitulo(String titulo);
    Optional<Livro> findByIsbn(String isbn);
    List<Livro> findByAnoDeLancamentoGreaterThan(int ano);
    List<Livro> findByAutorId (Long id);

    // Para queries mais complexas, use @Query:
    @Query("SELECT l FROM Livro l WHERE l.preco BETWEEN :min AND :max")
    List<Livro> findByFaixaDePreco(@Param("min") double min, @Param("max") double max);

}
