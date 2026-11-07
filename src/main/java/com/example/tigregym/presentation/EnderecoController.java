package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.EnderecoResponse;
import com.example.tigregym.application.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoResponse> buscarEndereco(@PathVariable String cep) {
        return new ResponseEntity.ok(buscarEndereco(cep));
    }
}
