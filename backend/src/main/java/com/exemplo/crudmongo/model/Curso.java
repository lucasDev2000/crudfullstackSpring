package com.exemplo.crudmongo.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer cargaHoraria;
    private Boolean ativo; 

    @ManyToMany(mappedBy = "cursos")
    // NOVO: Anotação para quebrar a recursão (Lado Inverso)
    @JsonBackReference
    private Set<Pessoa> pessoas = new HashSet<>();

    public Curso() {
        // construtor vazio pro JPA
    }

    public Curso(String nome, Integer cargaHoraria, boolean ativo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ativo = ativo;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    
    public Set<Pessoa> getPessoas() {
        return pessoas;
    }
    public void setPessoas(Set<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
