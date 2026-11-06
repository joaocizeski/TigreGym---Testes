package com.example.tigregym.infra.external;

import com.example.tigregym.application.DTOs.EnderecoResponse;
import com.example.tigregym.application.ports.EnderecoClientPort;
import com.example.tigregym.infra.dto.ViaCepResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class ViaCepClient implements EnderecoClientPort {

    private final HttpClient httpClient = HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EnderecoResponse consultarCep(String cep) {

        String url = "https://viacep.com.br/ws/"
                + cep.replaceAll("[^0-9]", "")
                + "/json/";

        try {

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {

                ViaCepResponse viaCepResponse = objectMapper.readValue(
                        response.body(),
                        ViaCepResponse.class
                );

                if (viaCepResponse.getErro() != null
                        && viaCepResponse.getErro()) {
                    throw new RuntimeException("CEP inválido ou inexistente.");
                }

                return new EnderecoResponse(
                        viaCepResponse.getCep(),
                        viaCepResponse.getLogradouro(),
                        viaCepResponse.getBairro(),
                        viaCepResponse.getLocalidade(),
                        viaCepResponse.getUf()
                );
            }

            throw new RuntimeException("Erro ao consultar a API do ViaCEP.");

        } catch (Exception e) {
            throw new RuntimeException(
                    "Falha na comunicação com o ViaCEP: " + e.getMessage()
            );
        }
    }
}
