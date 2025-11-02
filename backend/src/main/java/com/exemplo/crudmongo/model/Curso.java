package com.exemplo.crudmongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer cargaHoraria;

    private boolean ativo;

    public Curso() {
        // construtor vazio pro JPA
    }

    public Curso(String nome, Integer cargaHoraria, boolean ativo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ativo = ativo;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
