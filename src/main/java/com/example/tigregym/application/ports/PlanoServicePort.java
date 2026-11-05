package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.PlanoRequest;
import com.example.tigregym.application.DTOs.PlanoResponse;
import com.example.tigregym.domain.entities.EnumStatusPlano;

import java.util.List;

public interface PlanoServicePort {

    List<PlanoResponse> listarTodosPlanoTable();

    PlanoResponse salvarPlano(PlanoRequest plano, Long id);

    PlanoResponse buscarPorId(Long id);

    boolean atualizarStatus(EnumStatusPlano status, Long id);
}
