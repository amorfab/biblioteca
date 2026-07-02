package com.meucurso.biblioteca_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")          // nome da tabela (usa o nome da classe se omitir)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)    // coluna NOT NULL
    private String nome;


    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore   // evita loop infinito na serialização JSON
    private List<Livro> livros = new ArrayList<>();
}

