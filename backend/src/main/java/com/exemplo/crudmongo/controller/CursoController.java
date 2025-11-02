package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // LISTAR – aluno e coordenador podem
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public List<Curso> listar() {
        return service.listar();
    }

    // BUSCAR POR ID – aluno e coordenador podem
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public Curso buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // CRIAR – só coordenador
    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso criar(@RequestBody Curso curso) {
        return service.criar(curso);   // 👈 AQUI estava "salvar"
    }

    // ATUALIZAR – só coordenador
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    // DELETAR – só coordenador
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
