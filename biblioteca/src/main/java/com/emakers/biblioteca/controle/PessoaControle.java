package com.emakers.biblioteca.controle;

import com.emakers.biblioteca.model.Pessoa;
import com.emakers.biblioteca.servico.PessoaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaControle {

    @Autowired
    private PessoaServico pessoaServico;

    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return pessoaServico.salvar(pessoa);
    }

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaServico.listarTodas();
    }

    @GetMapping("/{id}")
    public Pessoa buscar(@PathVariable Long id) {
        return pessoaServico.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return pessoaServico.atualizar(id, pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pessoaServico.deletar(id);
    }

    // Rota HTTP POST para Empréstimo
    @PostMapping("/{idPessoa}/emprestar/{idLivro}")
    public void emprestar(@PathVariable Long idPessoa, @PathVariable Long idLivro) {
        pessoaServico.emprestarLivro(idPessoa, idLivro);
    }

    // Rota HTTP POST para Devolução
    @PostMapping("/{idPessoa}/devolver/{idLivro}")
    public void devolver(@PathVariable Long idPessoa, @PathVariable Long idLivro) {
        pessoaServico.devolverLivro(idPessoa, idLivro);
    }
}
