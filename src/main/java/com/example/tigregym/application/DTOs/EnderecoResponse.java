package com.example.tigregym.application.DTOs;

public record EnderecoResponse(
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf
) {
}
