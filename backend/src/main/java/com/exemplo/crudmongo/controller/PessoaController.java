package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas à entidade Pessoa.
 */
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

    // buscar por nome
    @GetMapping("/nome")
    public List<Pessoa> buscarPorNome(@RequestParam("valor") String valor) {
        return service.buscarPorNome(valor);
    }

    // buscar por idade
    // GET /pessoas/idade?valor=30
    @GetMapping("/idade")
    public List<Pessoa> buscarPorIdade(@RequestParam("valor") Integer valor) {
        return service.buscarPorIdade(valor);
    }

    // 👇 novo endpoint para paginação
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

}
