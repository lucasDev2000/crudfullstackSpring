package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/curso") // Define o endpoint base para as requisições
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (CORS)
public class CursoController {

    private final CursoService service; // Serviço responsável pela lógica de negócio

    /**
     * Injeta o serviço PessoaService via construtor.
     */
    public CursoController(CursoService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todas as pessoas cadastradas.
     * Método acessível via GET em /pessoas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    public List<Curso> listar() {
        return service.listarTodas();
    }

    
    /**
     * Cria uma nova pessoa.
     * Método acessível via POST em /pessoas
     * @param curso Objeto Pessoa recebido no corpo da requisição
     * @return Pessoa criada
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('COORDENADOR')")
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * Método acessível via PUT em /pessoas/{id}
     * @param id Identificador da pessoa a ser atualizada
     * @param curso Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR')")
    public Curso atualizar(@PathVariable Long id, 
    @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    /**
     * Exclui uma pessoa pelo ID.
     * Método acessível via DELETE em /pessoas/{id}
     * @param id Identificador da pessoa a ser excluída
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    @GetMapping("/filtrar")
    @PreAuthorize("hasAnyRole('ALUNO', COORDENADOR')")
    public Page<Curso> filtrar(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int cargaHoraria,
            @RequestParam(required = false) boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.buscarPorFiltros(nome, cargaHoraria, ativo, page, size);
    }
}
