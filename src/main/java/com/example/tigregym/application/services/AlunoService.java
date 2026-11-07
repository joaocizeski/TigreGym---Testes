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

        return alunoRepository
                .findByEmpresa_IdOrderByStatusAsc(empresa.getId())
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
            alunoBanco.setCep(aluno.cep());
            alunoBanco.setLogradouro(aluno.logradouro());

            if (aluno.numero() == null || aluno.numero().isBlank()) {
                alunoBanco.setNumero("S/N");
            } else if (aluno.numero().matches("\\d+")) {
                alunoBanco.setNumero(aluno.numero());
            } else {
                throw new IllegalArgumentException("Número deve conter apenas números!");
            }

            alunoBanco.setComplemento(aluno.complemento());
            alunoBanco.setBairro(aluno.bairro());
            alunoBanco.setCidade(aluno.cidade());
            alunoBanco.setUf(aluno.uf());
            alunoBanco.setEmpresa(empresa);

            alunoRepository.save(alunoBanco);
            return new AlunoResponse(alunoBanco);
        }

        Aluno alunoBanco = alunoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusAluno.EXCLUIDO
                )
                .orElse(null);

        if (alunoBanco != null) {
            alunoBanco.setNome(aluno.nome());
            alunoBanco.setCpf(new CPF(aluno.cpf()));
            alunoBanco.setDataNascimento(aluno.dataNascimento());
            alunoBanco.setTelefone(new Telefone(aluno.telefone()));
            alunoBanco.setEmail(new Email(aluno.email()));
            alunoBanco.setCep(aluno.cep());
            alunoBanco.setLogradouro(aluno.logradouro());

            if (aluno.numero() == null || aluno.numero().isBlank()) {
                alunoBanco.setNumero("S/N");
            } else if (aluno.numero().matches("\\d+")) {
                alunoBanco.setNumero(aluno.numero());
            } else {
                throw new IllegalArgumentException("Número deve conter apenas números!");
            }

            alunoBanco.setComplemento(aluno.complemento());
            alunoBanco.setBairro(aluno.bairro());
            alunoBanco.setCidade(aluno.cidade());
            alunoBanco.setUf(aluno.uf());

            alunoRepository.save(alunoBanco);
            return new AlunoResponse(alunoBanco);
        }

        return null;
    }

    @Override
    public AlunoResponse buscarPorId(Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Aluno aluno = alunoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusAluno.EXCLUIDO
                )
                .orElse(null);

        if (aluno == null) {
            return null;
        }

        return new AlunoResponse(aluno);
    }

    @Override
    public boolean atualizarStatus(EnumStatusAluno status, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Aluno alunoBanco = alunoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusAluno.EXCLUIDO
                )
                .orElse(null);

        if (alunoBanco != null) {
            alunoBanco.setStatus(status);
            alunoRepository.save(alunoBanco);
            return true;
        }

        return false;
    }
}
