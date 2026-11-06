package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.EnderecoResponse;

public interface EnderecoClientPort {

    EnderecoResponse consultarCep(String cep);
}
