package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.EnderecoResponse;
import com.example.tigregym.domain.interfaces.IVIaCepExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private IVIaCepExternalRepository vIaCepExternalRepository;

   public ResponseEntity<EnderecoResponse> BuscarEndereço(String cep){

        var enderecoCompleto = vIaCepExternalRepository.buscarEnderecoViaCepPorCep(cep);

        return new EnderecoResponse( enderecoCompleto.getCep(),
                enderecoCompleto.getLogradouro(),
                enderecoCompleto.getBairro(),
                enderecoCompleto.getLocalidade(),
                enderecoCompleto.getUf());
   }

}
