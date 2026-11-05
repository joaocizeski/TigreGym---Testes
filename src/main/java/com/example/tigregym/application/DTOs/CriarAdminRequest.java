package com.example.tigregym.application.DTOs;

public record CriarAdminRequest(
        String nome,
        String email,
        String senha,
        String cpf,
        String secretKey,
        String razaoSocial,
        String nomeFantasia,
        String cnpj
) {
}
