package com.emakers.biblioteca.servico;

import com.emakers.biblioteca.model.Pessoa;
import com.emakers.biblioteca.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServico implements UserDetailsService {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Busca a pessoa no banco usando o email do login
        Pessoa pessoa = pessoaRepositorio.findAll().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));


        return User.builder()
                .username(pessoa.getEmail())
                .password(pessoa.getSenha()) 
                .roles("USER")
                .build();
    }
}