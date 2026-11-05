package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.CepResponse;
import com.example.tigregym.application.ports.CepClientPort;
import com.example.tigregym.application.ports.CepServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CepService implements CepServicePort {

    @Autowired
    private CepClientPort cepClientPort;

    @Override
    public CepResponse buscarCep(String cep) {

        String cepFormatado = cep == null ? "" : cep.replaceAll("\\D", "");

        if (!cepFormatado.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido!");
        }

        CepResponse resposta = cepClientPort.buscarCep(cepFormatado);

        if (resposta == null || Boolean.TRUE.equals(resposta.erro())) {
            throw new IllegalArgumentException("CEP não encontrado!");
        }

        return resposta;
    }
}
