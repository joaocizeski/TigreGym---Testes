package com.example.tigregym.domain.interfaces;

import com.example.tigregym.infra.dto.ViaCepResponse;

public interface IVIaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);

}
