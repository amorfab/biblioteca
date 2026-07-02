package com.meucurso.biblioteca_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroResponse {
    private Long id;
    private String titulo;
    private String isbn;
    private int anoDeLancamento;
    private double preco;
    private String nomeDoAutor;   // dado conveniente para o cliente — sem objeto aninhado
}
