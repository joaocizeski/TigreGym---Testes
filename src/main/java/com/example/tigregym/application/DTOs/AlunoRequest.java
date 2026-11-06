package com.example.tigregym.application.DTOs;

import java.time.LocalDate;

public record AlunoRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        String email,
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf
) {
}
