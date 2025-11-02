package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> listar() {
        return repository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public Curso criar(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizar(Long id, Curso cursoAtualizado) {
        Curso curso = buscarPorId(id);
        curso.setNome(cursoAtualizado.getNome());
        curso.setCargaHoraria(cursoAtualizado.getCargaHoraria());
        curso.setAtivo(cursoAtualizado.isAtivo());
        return repository.save(curso);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
