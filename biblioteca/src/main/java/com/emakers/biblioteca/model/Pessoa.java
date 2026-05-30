package com.emakers.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pessoa {

    @Id //define chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;
    
    private String nome;  //colunas de pessoa/atributos
    private String cpf;
    private String cep;
    private String email;
    private String senha;

    @ManyToMany //faz relacionamento muitos pra muitos
    @JoinTable( //cria tabela intermediária, um tipo de relacionamento entre entidades pessoa e livro
        name = "emprestimo",
        joinColumns = @JoinColumn(name = "id_pessoa"),
        inverseJoinColumns = @JoinColumn(name = "id_livro")
    )
    private List<Livro> livrosEmprestados = new ArrayList<>();
}
