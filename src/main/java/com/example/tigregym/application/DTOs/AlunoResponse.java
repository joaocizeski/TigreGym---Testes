package com.example.tigregym.application.DTOs;

import com.example.tigregym.domain.entities.Aluno;
import com.example.tigregym.domain.entities.EnumStatusAluno;

import java.time.LocalDate;

public record AlunoResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        String email,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        EnumStatusAluno status
) {

    public AlunoResponse(Aluno alunoEntidade) {
        this(
                alunoEntidade.getId(),
                alunoEntidade.getNome(),
                alunoEntidade.getCpf().toString(),
                alunoEntidade.getDataNascimento(),
                alunoEntidade.getTelefone().toString(),
                alunoEntidade.getEmail().toString(),
                alunoEntidade.getCep(),
                alunoEntidade.getLogradouro(),
                alunoEntidade.getNumero() == null || alunoEntidade.getNumero().isBlank()
                        ? "S/N"
                        : alunoEntidade.getNumero(),
                alunoEntidade.getComplemento() == null
                        ? ""
                        : alunoEntidade.getComplemento(),
                alunoEntidade.getBairro(),
                alunoEntidade.getCidade(),
                alunoEntidade.getUf(),
                alunoEntidade.getStatus()
        );
    }
}
