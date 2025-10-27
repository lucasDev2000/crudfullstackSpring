package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Aluno;
import com.exemplo.crudmongo.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    public Aluno salvar(Aluno aluno) {
        return repository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno novoAluno) {
        return repository.findById(id).map(a -> {
            a.setNome(novoAluno.getNome());
            return repository.save(a);
        }).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

