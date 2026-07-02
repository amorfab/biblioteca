package com.meucurso.biblioteca_api.service;

import com.meucurso.biblioteca_api.dto.LivroRequest;
import com.meucurso.biblioteca_api.dto.LivroResponse;
import com.meucurso.biblioteca_api.exception.AutorNaoEncontradoException;
import com.meucurso.biblioteca_api.exception.LivroNaoEncontradoException;
import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.model.Livro;
import com.meucurso.biblioteca_api.repository.AutorRepository;
import com.meucurso.biblioteca_api.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public List<LivroResponse> listarTodos(){

        return livroRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LivroResponse buscarPorIdOuFalhar(Long id) {
        return livroRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public LivroResponse salvar(LivroRequest request){
        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new AutorNaoEncontradoException(request.getAutorId()));
        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setIsbn(request.getIsbn());
        livro.setAnoDeLancamento(request.getAnoDeLancamento());
        livro.setPreco(request.getPreco());
        livro.setAutor(autor);

        return toResponse(livroRepository.save(livro));
    }

    public Optional<LivroResponse> atualizar(Long id, LivroRequest request){

        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(request.getTitulo());
                    livro.setIsbn(request.getIsbn());
                    livro.setAnoDeLancamento(request.getAnoDeLancamento());
                    livro.setPreco(request.getPreco());
                    Autor autor = autorRepository.findById(request.getAutorId())
                            .orElseThrow(() -> new AutorNaoEncontradoException(request.getAutorId()));
                    livro.setAutor(autor);
                    return toResponse(livroRepository.save(livro));
                });
    }

    public boolean deletar(Long id){
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;   // encontrou e deletou
        }
        return false;      // não encontrou
    }

    private LivroResponse toResponse(Livro livro){
        LivroResponse response = new LivroResponse();
        response.setId(livro.getId());
        response.setTitulo(livro.getTitulo());
        response.setIsbn(livro.getIsbn());
        response.setAnoDeLancamento(livro.getAnoDeLancamento());
        response.setPreco(livro.getPreco());
        response.setNomeDoAutor(livro.getAutor().getNome());
        return response;
    }
}
