package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.model.Pessoa; 
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pessoa> listar() {
        return service.listarTodas();
    }
    
    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return service.salvar(pessoa);
    }
    
    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }
    
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
    
    @GetMapping("/nome")
    public List<Pessoa> buscarPorNome(@RequestParam("valor") String valor) {
        return service.buscarPorNome(valor);
    }
    
    @GetMapping("/idade")
    public List<Pessoa> buscarPorIdade(@RequestParam("valor") Integer valor) {
        return service.buscarPorIdade(valor);
    }

    @GetMapping("/pagina")
    public ResponseEntity<Map<String, Object>> paginar(
            @RequestParam(defaultValue = "0") Integer numero,
            @RequestParam(defaultValue = "10") Integer tamanho
    ) {
        Page<Pessoa> page = service.paginar(numero, tamanho);
        Map<String, Object> response = new HashMap<>();
        response.put("conteudo", page.getContent());
        response.put("pagina", page.getNumber());
        response.put("tamanho", page.getSize());
        response.put("totalElementos", page.getTotalElements());
        response.put("totalPaginas", page.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/curso")
    public List<Pessoa> buscarPorCurso(@RequestParam("valor") String curso) {
        return service.buscarPorCurso(curso);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<Map<String, Object>> pesquisarComFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanho
    ) {
        Page<Pessoa> page = service.pesquisarComFiltros(nome, curso, idadeMin, idadeMax, pagina, tamanho);
        Map<String, Object> response = new HashMap<>();
        response.put("conteudo", page.getContent());
        response.put("pagina", page.getNumber());
        response.put("tamanho", page.getSize());
        response.put("totalElementos", page.getTotalElements());
        response.put("totalPaginas", page.getTotalPages());
        return ResponseEntity.ok(response);
    }
 
    @GetMapping("/relatorio")
    public ResponseEntity<Map<String, Object>> relatorio() {
        Map<String, Object> relatorio = service.gerarRelatorio();
        return ResponseEntity.ok(relatorio);
    }
}
