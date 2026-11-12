package com.example.tigregym.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    private String cnpj;

    private String nomeFantasia;

    @OneToMany(mappedBy = "empresa")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "empresa")
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "empresa")
    private List<Plano> planos;

    @OneToMany(mappedBy = "empresa")
    private List<Matricula> matriculas;
}
