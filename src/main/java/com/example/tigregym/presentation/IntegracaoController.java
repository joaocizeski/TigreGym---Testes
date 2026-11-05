package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.CepResponse;
import com.example.tigregym.application.ports.CepServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integracoes")
@Tag(name = "Integrações", description = "Integrações externas utilizadas pelo TigreGym")
public class IntegracaoController {

    @Autowired
    private CepServicePort cepService;

    @GetMapping("/cep/{cep}")
    @Operation(
            summary = "Consulta CEP no ViaCEP",
            description = "Consulta um CEP utilizando o serviço externo ViaCEP"
    )
    public ResponseEntity<?> buscarCep(@PathVariable String cep) {

        try {
            CepResponse resposta = cepService.buscarCep(cep);
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
