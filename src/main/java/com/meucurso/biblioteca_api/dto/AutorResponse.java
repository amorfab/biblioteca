package com.meucurso.biblioteca_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorResponse {
    private Long id;
    private String nome;
    private String nacionalidade;
    private Long totalDeLivros;
}
