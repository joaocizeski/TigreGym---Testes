package com.example.tigregym.application.DTOs;

import com.example.tigregym.domain.entities.EnumStatusUsuario;
import com.example.tigregym.domain.entities.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        EnumStatusUsuario status
) {

    public UsuarioResponse(Usuario usuarioEntidade) {
        this(
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf().toString(),
                usuarioEntidade.getEmail().toString(),
                usuarioEntidade.getStatus()
        );
    }
}
