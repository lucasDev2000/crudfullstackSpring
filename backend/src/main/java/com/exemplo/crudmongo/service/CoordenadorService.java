package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Coordenador;
import com.exemplo.crudmongo.repository.CoordenadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoordenadorService {

    private final CoordenadorRepository repository;

    public CoordenadorService(CoordenadorRepository repository) {
        this.repository = repository;
    }

    public List<Coordenador> listarTodos() {
        return repository.findAll();
    }

    public Coordenador salvar(Coordenador coordenador) {
        return repository.save(coordenador);
    }

    public Coordenador atualizar(Long id, Coordenador novoCoordenador) {
        return repository.findById(id).map(c -> {
            c.setNome(novoCoordenador.getNome());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

