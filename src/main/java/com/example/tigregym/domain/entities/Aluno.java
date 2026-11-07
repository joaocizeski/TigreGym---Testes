package com.example.tigregym.domain.entities;

import com.example.tigregym.domain.valueobject.CPF;
import com.example.tigregym.domain.valueobject.Email;
import com.example.tigregym.domain.valueobject.Telefone;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String nome;

    @Embedded
    public CPF cpf;

    public LocalDate dataNascimento;

    @Embedded
    public Telefone telefone;

    @Embedded
    public Email email;

    public String cep;

    public String logradouro;

    public String numero;

    public String bairro;

    public String cidade;

    public String uf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @OneToMany(mappedBy = "aluno")
    private Set<Matricula> matriculas = new HashSet<>();

    public EnumStatusAluno status = EnumStatusAluno.ATIVO;
}
