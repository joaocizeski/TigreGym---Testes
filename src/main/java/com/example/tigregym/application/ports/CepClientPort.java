package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.CepResponse;

public interface CepClientPort {

    CepResponse buscarCep(String cep);
}
