package com.meucurso.biblioteca_api.controller;

import com.meucurso.biblioteca_api.dto.LivroRequest;
import com.meucurso.biblioteca_api.dto.LivroResponse;
import com.meucurso.biblioteca_api.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController {
    private final LivroService service;

    @GetMapping
    public List<LivroResponse> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorIdOuFalhar(id));
    }

    @PostMapping
    public ResponseEntity<LivroResponse> criar(@Valid @RequestBody LivroRequest livro){
        return ResponseEntity.status(201).body(service.salvar(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable Long id,@Valid @RequestBody LivroRequest livro){
        return service.atualizar(id, livro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (service.deletar(id)){
            return ResponseEntity.noContent().build(); //204 - deletou com sucesso
        }
        return ResponseEntity.notFound().build(); //404 - não encontrou o livro para deletar
    }
}
