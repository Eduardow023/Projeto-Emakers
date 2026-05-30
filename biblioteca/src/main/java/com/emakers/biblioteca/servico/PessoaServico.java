package com.emakers.biblioteca.servico;

import com.emakers.biblioteca.model.Livro;
import com.emakers.biblioteca.model.Pessoa;
import com.emakers.biblioteca.repositorio.LivroRepositorio;
import com.emakers.biblioteca.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
// lida com o agente ativo do sistema (a pessoa)
@Service
public class PessoaServico{

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private ViaCepServico viaCepServico;

    public Pessoa salvar(Pessoa pessoa){
        // Busca o endereço na API externa usando o CEP digitado no cadastro
        String enderecoCompleto = viaCepServico.buscarEnderecoPorCep(pessoa.getCep());
        
        pessoa.setEndereco(enderecoCompleto);
        
        return pessoaRepositorio.save(pessoa);
    }

    public List<Pessoa> listarTodas(){
        return pessoaRepositorio.findAll();
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setCep(pessoaAtualizada.getCep());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        pessoa.setSenha(pessoaAtualizada.getSenha());
        return pessoaRepositorio.save(pessoa);
    }

    public void deletar(Long id) {
        Pessoa pessoa = buscarPorId(id);
        pessoaRepositorio.delete(pessoa);
    }

    // Funcionalidade para pegar Livro emprestado (2 pontos)
    public void emprestarLivro(Long idPessoa, Long idLivro) {
        Pessoa pessoa = buscarPorId(idPessoa);
        Livro livro = livroRepositorio.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        pessoa.getLivrosEmprestados().add(livro);
        pessoaRepositorio.save(pessoa);
    }

    // Funcionalidade para devolver um Livro (2 pontos)
    public void devolverLivro(Long idPessoa, Long idLivro) {
        Pessoa pessoa = buscarPorId(idPessoa);
        Livro livro = livroRepositorio.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        pessoa.getLivrosEmprestados().remove(livro);
        pessoaRepositorio.save(pessoa);
    }
}