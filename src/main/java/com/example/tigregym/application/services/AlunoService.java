package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.AlunoRequest;
import com.example.tigregym.application.DTOs.AlunoResponse;
import com.example.tigregym.application.ports.AlunoServicePort;
import com.example.tigregym.domain.entities.Aluno;
import com.example.tigregym.domain.entities.Empresa;
import com.example.tigregym.domain.entities.EnumStatusAluno;
import com.example.tigregym.domain.repository.AlunoRepository;
import com.example.tigregym.domain.valueobject.CPF;
import com.example.tigregym.domain.valueobject.Email;
import com.example.tigregym.domain.valueobject.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService implements AlunoServicePort {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public List<AlunoResponse> listarTodosAlunoTable() {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        if (empresa == null) {
            return alunoRepository.findByStatusNot(EnumStatusAluno.EXCLUIDO)
                    .orElse(List.of())
                    .stream()
                    .map(AlunoResponse::new)
                    .toList();
        }

        return alunoRepository
                .findByEmpresa_IdAndStatusNot(
                        empresa.getId(),
                        EnumStatusAluno.EXCLUIDO
                )
                .stream()
                .map(AlunoResponse::new)
                .toList();
    }

    @Override
    public AlunoResponse salvarAluno(AlunoRequest aluno, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        if (id == 0L) {
            Aluno alunoBanco = new Aluno();
            alunoBanco.setNome(aluno.nome());
            alunoBanco.setCpf(new CPF(aluno.cpf()));
            alunoBanco.setDataNascimento(aluno.dataNascimento());
            alunoBanco.setTelefone(new Telefone(aluno.telefone()));
            alunoBanco.setEmail(new Email(aluno.email()));

            if (empresa != null) {
                alunoBanco.setEmpresa(empresa);
            }

            alunoRepository.save(alunoBanco);

            return new AlunoResponse(alunoBanco);
        }

        Aluno alunoBanco;

        if (empresa == null) {
            alunoBanco = alunoRepository.findById(id).orElse(null);
        } else {
            alunoBanco = alunoRepository
                    .findByIdAndEmpresa_IdAndStatusNot(
                            id,
                            empresa.getId(),
                            EnumStatusAluno.EXCLUIDO
                    )
                    .orElse(null);
        }

        if (alunoBanco != null) {
            alunoBanco.setNome(aluno.nome());
            alunoBanco.setCpf(new CPF(aluno.cpf()));
            alunoBanco.setDataNascimento(aluno.dataNascimento());
            alunoBanco.setTelefone(new Telefone(aluno.telefone()));
            alunoBanco.setEmail(new Email(aluno.email()));

            alunoRepository.save(alunoBanco);

            return new AlunoResponse(alunoBanco);
        }

        return null;
    }

    @Override
    public AlunoResponse buscarPorId(Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();
        Aluno aluno;

        if (empresa == null) {
            aluno = alunoRepository.findById(id).orElse(null);
        } else {
            aluno = alunoRepository
                    .findByIdAndEmpresa_IdAndStatusNot(
                            id,
                            empresa.getId(),
                            EnumStatusAluno.EXCLUIDO
                    )
                    .orElse(null);
        }

        if (aluno == null) {
            return null;
        }

        return new AlunoResponse(aluno);
    }

    @Override
    public boolean atualizarStatus(EnumStatusAluno status, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();
        Aluno alunoBanco;

        if (empresa == null) {
            alunoBanco = alunoRepository.findById(id).orElse(null);
        } else {
            alunoBanco = alunoRepository
                    .findByIdAndEmpresa_IdAndStatusNot(
                            id,
                            empresa.getId(),
                            EnumStatusAluno.EXCLUIDO
                    )
                    .orElse(null);
        }

        if (alunoBanco != null) {
            alunoBanco.setStatus(status);
            alunoRepository.save(alunoBanco);
            return true;
        }

        return false;
    }
}
