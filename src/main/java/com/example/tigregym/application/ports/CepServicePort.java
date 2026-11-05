package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.CepResponse;

public interface CepServicePort {

    CepResponse buscarCep(String cep);
}
