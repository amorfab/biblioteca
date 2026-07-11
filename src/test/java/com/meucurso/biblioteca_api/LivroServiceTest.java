package com.meucurso.biblioteca_api;

import com.meucurso.biblioteca_api.dto.LivroRequest;
import com.meucurso.biblioteca_api.dto.LivroResponse;
import com.meucurso.biblioteca_api.exception.AutorNaoEncontradoException;
import com.meucurso.biblioteca_api.exception.LivroNaoEncontradoException;
import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.model.Livro;
import com.meucurso.biblioteca_api.repository.AutorRepository;
import com.meucurso.biblioteca_api.repository.LivroRepository;
import com.meucurso.biblioteca_api.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;  // mock — não vai ao banco

    @Mock
    private AutorRepository autorRepository;  // mock — não vai ao banco

    @InjectMocks
    private LivroService service; // @InjectMocks cria o LivroService e injeta os mocks acima automaticamente

    @Test
    @DisplayName("Deve retornar LivroResponse quando livro encontrado")
    void deveBuscarLivroPorId() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Robert C. Martin");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor(autor);

        // "quando findById(1L) for chamado, retorne este livro"
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        LivroResponse response = service.buscarPorIdOuFalhar(1L);
        assertEquals("Clean Code", response.getTitulo());
        assertEquals("Robert C. Martin", response.getNomeDoAutor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando livro não encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(LivroNaoEncontradoException.class,
                () -> service.buscarPorIdOuFalhar(99L));
    }

    @Test
    @DisplayName("Deve lançar AutorNaoEncontradoException quando autor não existe")
    void deveLancarExcecaoQuandoAutorNaoExiste(){
        LivroRequest request = new LivroRequest();
        request.setTitulo("Clean Code");
        request.setAutorId(99L);

        when(autorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AutorNaoEncontradoException.class,
                () -> service.salvar(request));
    }

    @Test
    @DisplayName("Deve chamar save ao salvar livro com sucesso")
    void deveSalvarLivroComSucesso(){
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Robert C. Martin");

        LivroRequest request = new LivroRequest();
        request.setTitulo("Clean Code");
        request.setIsbn("978-0132350884");
        request.setAnoDeLancamento(2008);
        request.setPreco(89.90);
        request.setAutorId(1L);

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any(Livro.class))).thenAnswer(inv -> {
            Livro l = inv.getArgument(0);
            l.setId(1L);
            return l;
        });

        service.salvar(request);

        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve retornar true e chamar deleteById quando livro existe")
    void deveRetornarTrueQuandoExiste() {
//        Autor autor = new Autor();
//        autor.setId(1L);
//        autor.setNome("Robert C. Martin");
//
//        Livro livro = new Livro();
//        livro.setId(1L);
//        livro.setTitulo("Clean Code");
//        livro.setAutor(autor);

        when(livroRepository.existsById(1L)).thenReturn(true);

        boolean resultado = service.deletar(1L);

        assertTrue(resultado);
        verify(livroRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar false e não chamar deleteById quando livro não existe")
    void deveRetornarFalseQuandoNaoExiste() {
        when(livroRepository.existsById(99L)).thenReturn(false);

        boolean resultado = service.deletar(99L);

        assertFalse(resultado);
        verify(livroRepository, never()).deleteById(anyLong());
    }

}