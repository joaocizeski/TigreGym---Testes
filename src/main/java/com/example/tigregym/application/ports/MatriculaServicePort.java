package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.MatriculaRequest;
import com.example.tigregym.application.DTOs.MatriculaResponse;
import com.example.tigregym.domain.entities.EnumStatusMatricula;

import java.util.List;

public interface MatriculaServicePort {

    List<MatriculaResponse> listarTodosMatriculaTable();

    MatriculaResponse salvarMatricula(MatriculaRequest matricula, Long id);

    MatriculaResponse buscarPorId(Long id);

    boolean atualizarStatus(EnumStatusMatricula status, Long id);
}
