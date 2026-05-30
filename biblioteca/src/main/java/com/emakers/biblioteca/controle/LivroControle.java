package com.emakers.biblioteca.controle;

import com.emakers.biblioteca.model.Livro;
import com.emakers.biblioteca.servico.LivroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// gerencia a comunicação web / prepara para LivroServico

@RestController
@RequestMapping("/livros")
public class LivroControle {

    @Autowired
    private LivroServico livroServico;

    @PostMapping
    public Livro criar(@RequestBody Livro livro) {
        return livroServico.salvar(livro);
    }

    @GetMapping
    public List<Livro> listar() {
        return livroServico.listarTodos();
    }

    @GetMapping("/{id}")
    public Livro buscar(@PathVariable Long id) {
        return livroServico.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        return livroServico.atualizar(id, livro);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        livroServico.deletar(id);
    }
}