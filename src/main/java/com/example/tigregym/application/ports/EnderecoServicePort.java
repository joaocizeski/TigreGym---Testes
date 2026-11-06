package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.EnderecoResponse;

public interface EnderecoServicePort {

    EnderecoResponse buscarEnderecoFormatado(String cep);
}
