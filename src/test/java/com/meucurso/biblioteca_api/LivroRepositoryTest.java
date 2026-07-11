package com.meucurso.biblioteca_api;

import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.model.Livro;
import com.meucurso.biblioteca_api.repository.AutorRepository;
import com.meucurso.biblioteca_api.repository.LivroRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setNome("Robert C. Martin");
        autorRepository.save(autor);
    }

    @Test
    @DisplayName("Deve salvar livro e gerar ID automaticamente")
    void deveSalvarLivroEGerarId() {
        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setIsbn("978-0132350884");
        livro.setAutor(autor);

        Livro salvo = livroRepository.save(livro);

        assertNotNull(salvo.getId());
        assertEquals("Clean Code", salvo.getTitulo());
    }

    @Test
    @DisplayName("Deve buscar livro por ISBN existente")
    void deveBuscarPorIsbnExistente() {
        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setIsbn("978-0132350884");
        livro.setAutor(autor);
        livroRepository.save(livro);

        Optional<Livro> encontrado = livroRepository.findByIsbn("978-0132350884");
        assertTrue(encontrado.isPresent());

    }

    @Test
    @DisplayName("Deve buscar livro por ISBN inexistente")
    void deveRetornarVazioParaIsbnInexistente() {

        Optional<Livro> encontrado = livroRepository.findByIsbn("isbn-inesxistente");
        assertTrue(encontrado.isEmpty());

    }
}
