package com.example.tigregym.application.ports;

import com.example.tigregym.application.DTOs.AlunoRequest;
import com.example.tigregym.application.DTOs.AlunoResponse;
import com.example.tigregym.domain.entities.EnumStatusAluno;

import java.util.List;

public interface AlunoServicePort {

    List<AlunoResponse> listarTodosAlunoTable();

    AlunoResponse salvarAluno(AlunoRequest aluno, Long id);

    AlunoResponse buscarPorId(Long id);

    boolean atualizarStatus(EnumStatusAluno status, Long id);
}
