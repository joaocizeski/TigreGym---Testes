package com.example.tigregym.infra.integration;

import com.example.tigregym.application.DTOs.CepResponse;
import com.example.tigregym.application.ports.CepClientPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepClient implements CepClientPort {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://viacep.com.br")
            .build();

    @Override
    public CepResponse buscarCep(String cep) {

        return restClient.get()
                .uri("/ws/{cep}/json/", cep)
                .retrieve()
                .body(CepResponse.class);
    }
}
