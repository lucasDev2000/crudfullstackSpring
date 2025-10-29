package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;   
import org.springframework.data.domain.Sort;      
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listarTodas() {
        return repository.findAll();
    }

    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    // Não use @PathVariable em service
    public Pessoa atualizar(Long id, Pessoa novaPessoa) {
        return repository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // Buscar por nome (contém, ignorando maiúsc/minúsc)
    public List<Pessoa> buscarPorNome(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Parâmetro 'valor' (nome) é obrigatório.");
        }
        return repository.findByNomeContainingIgnoreCase(valor);
    }

    // Buscar por idade
    public List<Pessoa> buscarPorIdade(Integer valor) {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("Parâmetro 'valor' (idade) deve ser um inteiro >= 0.");
        }
        return repository.findByIdade(valor);
    }

    // Paginação
    public Page<Pessoa> paginar(Integer numero, Integer tamanho) {
        if (numero == null || numero < 1) {
            throw new IllegalArgumentException("Parâmetro 'numero' (número da página) deve ser >= 1.");
        }
        if (tamanho == null || tamanho < 1) {
            throw new IllegalArgumentException("Parâmetro 'tamanho' (tamanho da página) deve ser >= 1.");
        }

        // numero é 1-based na API → 0-based no Spring
        Pageable pageable = PageRequest.of(numero - 1, tamanho, Sort.by("id").ascending());
        return repository.findAll(pageable);
    }
}
