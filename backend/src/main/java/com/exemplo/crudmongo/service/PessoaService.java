package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.model.Pessoa; 
import com.exemplo.crudmongo.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;   
import org.springframework.data.domain.Sort;      
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    public Pessoa atualizar(Long id, Pessoa novaPessoa) {
        return repository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            p.setCursos(novaPessoa.getCursos()); 
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<Pessoa> buscarPorNome(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Parâmetro 'valor' (nome) é obrigatório.");
        }
        return repository.findByNomeContainingIgnoreCase(valor);
    }

    public List<Pessoa> buscarPorIdade(Integer valor) {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("Parâmetro 'valor' (idade) deve ser um inteiro >= 0.");
        }
        return repository.findByIdade(valor);
    }

    public Page<Pessoa> paginar(Integer numero, Integer tamanho) {
        if (numero == null || numero < 0) { 
            throw new IllegalArgumentException("Parâmetro 'numero' (número da página) deve ser >= 0.");
        }
        if (tamanho == null || tamanho < 1) {
            throw new IllegalArgumentException("Parâmetro 'tamanho' (tamanho da página) deve ser >= 1.");
        }
        Pageable pageable = PageRequest.of(numero, tamanho, Sort.by("id").ascending()); 
        return repository.findAll(pageable);
    }
  
    public List<Pessoa> buscarPorCurso(String curso) {
        if (curso == null || curso.isBlank()) {
            throw new IllegalArgumentException("Parâmetro 'curso' é obrigatório.");
        }
        return repository.buscarPorNomeDeCurso(curso);
    }
  
    public Page<Pessoa> pesquisarComFiltros(String nome,
                                            String curso,
                                            Integer idadeMin,
                                            Integer idadeMax,
                                            Integer pagina,
                                            Integer tamanho) {
        if (pagina == null || pagina < 0) pagina = 0;
        if (tamanho == null || tamanho < 1) tamanho = 10; 
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome").ascending());

        return repository.pesquisarComFiltros(
                isBlank(nome) ? null : nome,
                isBlank(curso) ? null : curso,
                idadeMin,
                idadeMax,
                pageable
        );
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public Map<String, Object> gerarRelatorio() {
        Map<String, Object> relatorio = new LinkedHashMap<>();

        long totalPessoas = repository.contarTotalPessoas();
        relatorio.put("totalPessoas", totalPessoas);

        List<Object[]> totalPorCursoRaw = repository.contarPorCurso();
        List<Map<String, Object>> totalPorCursoFormatado = new ArrayList<>();
        for (Object[] linha : totalPorCursoRaw) {
            Map<String, Object> item = new HashMap<>();
            item.put("curso", linha[0]);
            item.put("totalPessoas", linha[1]);
            totalPorCursoFormatado.add(item);
        }
        relatorio.put("totalPorCurso", totalPorCursoFormatado);

        List<Object[]> mediaPorCursoRaw = repository.mediaIdadePorCurso();
        List<Map<String, Object>> mediaPorCursoFormatado = new ArrayList<>();
        for (Object[] linha : mediaPorCursoRaw) {
            Map<String, Object> item = new HashMap<>();
            item.put("curso", linha[0]);
            item.put("mediaIdade", linha[1]);
            mediaPorCursoFormatado.add(item);
        }
        relatorio.put("mediaIdadePorCurso", mediaPorCursoFormatado);

        return relatorio;
    }
}
