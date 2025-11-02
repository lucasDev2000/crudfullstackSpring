package com.exemplo.crudmongo.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nome;
    private int idade;

    @ManyToMany
    @JoinTable(
            name = "pessoa_curso", 
            joinColumns = @JoinColumn(name = "pessoa_id"), 
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonManagedReference
    private Set<Curso> cursos = new HashSet<>();

    public Pessoa() {
        // Construtor vazio
    } 

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Set<Curso> getCursos() {
        return cursos;
    }
    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }
}
