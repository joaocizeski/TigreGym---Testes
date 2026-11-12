package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.MatriculaRequest;
import com.example.tigregym.application.DTOs.MatriculaResponse;
import com.example.tigregym.domain.entities.Aluno;
import com.example.tigregym.domain.entities.Empresa;
import com.example.tigregym.domain.entities.EnumStatusAluno;
import com.example.tigregym.domain.entities.EnumStatusMatricula;
import com.example.tigregym.domain.entities.EnumStatusPlano;
import com.example.tigregym.domain.entities.Matricula;
import com.example.tigregym.domain.entities.Plano;
import com.example.tigregym.domain.repository.AlunoRepository;
import com.example.tigregym.domain.repository.MatriculaRepository;
import com.example.tigregym.domain.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<MatriculaResponse> listarTodosMatriculaTable() {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        return matriculaRepository
                .findByEmpresa_IdOrderByStatusAsc(empresa.getId())
                .stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    public MatriculaResponse salvarMatricula(MatriculaRequest matricula, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Aluno alunoBanco = alunoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        matricula.aluno_id(),
                        empresa.getId(),
                        EnumStatusAluno.EXCLUIDO
                )
                .orElse(null);

        Plano planoBanco = planoRepository
                .findByIdAndEmpresa_IdAndStatus(
                        matricula.plano_id(),
                        empresa.getId(),
                        EnumStatusPlano.ATIVO
                )
                .orElse(null);

        if (alunoBanco == null || planoBanco == null) {
            return null;
        }

        if (planoBanco.getDuracaoEmMeses() == null || planoBanco.getDuracaoEmMeses() < 1) {
            throw new IllegalArgumentException("O plano selecionado não possui duração válida!");
        }

        if (id == 0L) {
            Matricula matriculaBanco = new Matricula();
            matriculaBanco.setAluno(alunoBanco);
            matriculaBanco.setPlano(planoBanco);
            matriculaBanco.setEmpresa(empresa);

            LocalDateTime dataInicio = LocalDateTime.now();
            matriculaBanco.setDataInicio(dataInicio);
            matriculaBanco.setDataVencimento(
                    dataInicio.plusMonths(planoBanco.getDuracaoEmMeses())
            );

            matriculaRepository.save(matriculaBanco);
            return new MatriculaResponse(matriculaBanco);
        }

        Matricula matriculaBanco = matriculaRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusMatricula.EXCLUIDO
                )
                .orElse(null);

        if (matriculaBanco != null) {
            matriculaBanco.setAluno(alunoBanco);
            matriculaBanco.setPlano(planoBanco);

            LocalDateTime dataInicio = matriculaBanco.getDataInicio();

            if (dataInicio == null) {
                dataInicio = LocalDateTime.now();
                matriculaBanco.setDataInicio(dataInicio);
            }

            matriculaBanco.setDataVencimento(
                    dataInicio.plusMonths(planoBanco.getDuracaoEmMeses())
            );

            matriculaRepository.save(matriculaBanco);
            return new MatriculaResponse(matriculaBanco);
        }

        return null;
    }

    public MatriculaResponse buscarPorId(Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Matricula matricula = matriculaRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusMatricula.EXCLUIDO
                )
                .orElse(null);

        if (matricula == null) {
            return null;
        }

        return new MatriculaResponse(matricula);
    }

    public boolean atualizarStatus(EnumStatusMatricula status, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Matricula matriculaBanco = matriculaRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusMatricula.EXCLUIDO
                )
                .orElse(null);

        if (matriculaBanco != null) {
            matriculaBanco.setStatus(status);
            matriculaRepository.save(matriculaBanco);
            return true;
        }

        return false;
    }
}
