package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.CriarAdminRequest;
import com.example.tigregym.application.DTOs.CriarAdminResponse;
import com.example.tigregym.application.DTOs.LoginRequest;
import com.example.tigregym.application.DTOs.LoginResponse;
import com.example.tigregym.application.DTOs.UsuarioRequest;
import com.example.tigregym.application.DTOs.UsuarioResponse;
import com.example.tigregym.domain.entities.EnumStatusUsuario;

import java.util.List;

public interface UsuarioServicePort {

    LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request);

    List<UsuarioResponse> listarTodosUsuariosTable();

    CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest);

    UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id);

    UsuarioResponse buscarPorId(Long id);

    boolean atualizarStatus(EnumStatusUsuario status, Long id);

    UsuarioResponse BuscarUsuariologado();
}
