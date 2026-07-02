package com.meucurso.biblioteca_api.dto;

import com.meucurso.biblioteca_api.model.Autor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 2, max = 200, message = "Título deve ter entre 2 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "ISBN é obrigatório")
    private String isbn;

    @Min(value = 1450, message = "Ano inválido — livros existem desde 1450")
    @Max(value = 2100, message = "Ano inválido")
    private int anoDeLancamento;

    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private double preco;       // @Column é opcional para o padrão

    @NotNull(message = "Autor é obrigatório")
    private Long autorId;

}

