package com.emakers.biblioteca.servico;

import com.emakers.biblioteca.model.Livro;
import com.emakers.biblioteca.model.Pessoa;
import com.emakers.biblioteca.repositorio.LivroRepositorio;
import com.emakers.biblioteca.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PessoaServico {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private ViaCepServico viaCepServico;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public Pessoa salvar(Pessoa pessoa) {
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        String enderecoCompleto = viaCepServico.buscarEnderecoPorCep(pessoa.getCep());
        pessoa.setEndereco(enderecoCompleto);
        return pessoaRepositorio.save(pessoa);
    }

    public List<Pessoa> listarTodas() {
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

    public void emprestarLivro(Long idPessoa, Long idLivro) {
        Pessoa pessoa = buscarPorId(idPessoa);
        Livro livro = livroRepositorio.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        //1: Limitar a quantidade de livros emprestados por pessoa(Máximo 3)
        if (pessoa.getLivrosEmprestados().size() >= 3) {
            throw new RuntimeException("Limite atingido! Esta pessoa já possui 3 livros emprestados.");
        }

        //2:Verificar se há estoque disponível do livro
        if (livro.getQuantidade() <= 0) {
            throw new RuntimeException("Livro indisponível no estoque!");
        }

        //Executa o empréstimo e retira 1 da quantidade do estoque
        livro.decrementarQuantidade();
        pessoa.getLivrosEmprestados().add(livro);
        
        livroRepositorio.save(livro);
        pessoaRepositorio.save(pessoa);
    }

    public void devolverLivro(Long idPessoa, Long idLivro) {
        Pessoa pessoa = buscarPorId(idPessoa);
        Livro livro = livroRepositorio.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        
        //Verifica se a pessoa realmente está com esse livro antes de devolver
        if (!pessoa.getLivrosEmprestados().contains(livro)) {
            throw new RuntimeException("Este livro não está emprestado para esta pessoa.");
        }

        //Executa a devolução e soma 1 de volta ao estoque
        pessoa.getLivrosEmprestados().remove(livro);
        livro.incrementarQuantidade();
        
        livroRepositorio.save(livro);
        pessoaRepositorio.save(pessoa);
    }
}