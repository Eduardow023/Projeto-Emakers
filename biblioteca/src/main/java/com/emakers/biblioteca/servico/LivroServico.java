package com.emakers.biblioteca.servico;

import com.emakers.biblioteca.model.Livro;
import com.emakers.biblioteca.repositorio.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivroServico { //meio auto explicativo é uma classe com funções de salvar, listar e etc

    @Autowired
    private LivroRepositorio livroRepositorio;

    public Livro salvar(Livro livro) {
        return livroRepositorio.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepositorio.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);
        livro.setNome(livroAtualizado.getNome());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setDataLancamento(livroAtualizado.getDataLancamento());
        return livroRepositorio.save(livro);
    }

    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        livroRepositorio.delete(livro);
    }
}
