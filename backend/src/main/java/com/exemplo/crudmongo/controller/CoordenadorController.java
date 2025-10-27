package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Coordenador;
import com.exemplo.crudmongo.service.CoordenadorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/coordenador")
@CrossOrigin(origins = "*")
public class CoordenadorController {

    private final CoordenadorService service;

    public CoordenadorController(CoordenadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Coordenador> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Coordenador criar(@RequestBody Coordenador coordenador) {
        return service.salvar(coordenador);
    }

    @PutMapping("/{id}")
    public Coordenador atualizar(@PathVariable Long id, @RequestBody Coordenador coordenador) {
        return service.atualizar(id, coordenador);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
