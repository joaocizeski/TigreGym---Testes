package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.EnderecoResponse;
import com.example.tigregym.application.ports.EnderecoClientPort;
import com.example.tigregym.application.ports.EnderecoServicePort;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService implements EnderecoServicePort {

    private final EnderecoClientPort enderecoClient;

    public EnderecoService(EnderecoClientPort enderecoClient) {
        this.enderecoClient = enderecoClient;
    }

    @Override
    public EnderecoResponse buscarEnderecoFormatado(String cep) {
        return enderecoClient.consultarCep(cep);
    }
}
