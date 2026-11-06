package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.EnderecoResponse;
import com.example.tigregym.application.ports.EnderecoServicePort;
import com.example.tigregym.infra.dto.ViaCepResponse;
import com.example.tigregym.infra.external.ViaCepClient;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService implements EnderecoServicePort {

    private final ViaCepClient viaCepClient;

    public EnderecoService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    @Override
    public EnderecoResponse buscarEnderecoFormatado(String cep) {

        ViaCepResponse response = viaCepClient.consultarCep(cep);

        EnderecoResponse endereco = new EnderecoResponse(
                response.getCep(),
                response.getLogradouro(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf()
        );

        return endereco;
    }
}
