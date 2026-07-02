package com.meucurso.biblioteca_api.service;

import com.meucurso.biblioteca_api.dto.AutorRequest;
import com.meucurso.biblioteca_api.dto.AutorResponse;
import com.meucurso.biblioteca_api.exception.AutorNaoEncontradoException;
import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository repository;

    @Transactional(readOnly = true)  // ← mantém a transação aberta durante o método
    public List<AutorResponse> listarTodos(){
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)  // ← mantém a transação aberta durante o método
    public AutorResponse buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
    }

    public AutorResponse salvar(AutorRequest request){
        Autor autor = new Autor();
        autor.setNome(request.getNome());
        autor.setNacionalidade(request.getNacionalidade());
        return toResponse(repository.save(autor));
    }

    public Optional<AutorResponse> atualizar(Long id, AutorRequest request){
        return repository.findById(id)
                .map(autor -> {
                    autor.setNome(request.getNome());
                    autor.setNacionalidade(request.getNacionalidade());
                    return toResponse(repository.save(autor));
                });
    }

    public boolean deletar(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;   // encontrou e deletou
        }
        return false;      // não encontrou
    }

    private AutorResponse toResponse(Autor autor){
        AutorResponse response = new AutorResponse();
        response.setId(autor.getId());
        response.setNome(autor.getNome());
        response.setNacionalidade(autor.getNacionalidade());
        response.setTotalDeLivros((long) autor.getLivros().size());
        return response;
    }
}
