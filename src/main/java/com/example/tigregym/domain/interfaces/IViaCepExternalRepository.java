package com.example.tigregym.domain.interfaces;

import com.example.tigregym.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);

}
