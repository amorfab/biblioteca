package com.meucurso.biblioteca_api.controller;

import com.meucurso.biblioteca_api.dto.AutorRequest;
import com.meucurso.biblioteca_api.dto.AutorResponse;
import com.meucurso.biblioteca_api.model.Autor;
import com.meucurso.biblioteca_api.model.Livro;
import com.meucurso.biblioteca_api.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
public class AutorController {
    private final AutorService service;

    @GetMapping
    public List<AutorResponse> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorIdOuFalhar(id));
    }

    @PostMapping
    public ResponseEntity<AutorResponse> criar(@Valid @RequestBody AutorRequest autor){
        return ResponseEntity.status(201).body(service.salvar(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizar(@PathVariable Long id,@Valid @RequestBody AutorRequest autor){
        return service.atualizar(id, autor)
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
