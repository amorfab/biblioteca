package com.meucurso.biblioteca_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity                          // marca como entidade JPA (vira uma tabela)
@Table(name = "livros")          // nome da tabela (usa o nome da classe se omitir)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id                          // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto incremento
    private Long id;

    @Column(nullable = false)    // coluna NOT NULL
    private String titulo;

    @Column(unique = true)       // coluna UNIQUE
    private String isbn;

    @Column(name = "ano_de_lancamento")  // nome customizado da coluna
    private int anoDeLancamento;

    private double preco;       // @Column é opcional para o padrão

    @ManyToOne
    @JoinColumn(name = "autor_id")  // coluna de chave estrangeira
    private Autor autor;

}
