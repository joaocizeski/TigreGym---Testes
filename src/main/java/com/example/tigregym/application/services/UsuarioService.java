package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.CriarAdminRequest;
import com.example.tigregym.application.DTOs.CriarAdminResponse;
import com.example.tigregym.application.DTOs.LoginRequest;
import com.example.tigregym.application.DTOs.LoginResponse;
import com.example.tigregym.application.DTOs.UsuarioLogado;
import com.example.tigregym.application.DTOs.UsuarioRequest;
import com.example.tigregym.application.DTOs.UsuarioResponse;
import com.example.tigregym.application.ports.UsuarioServicePort;
import com.example.tigregym.domain.entities.Empresa;
import com.example.tigregym.domain.entities.EnumStatusUsuario;
import com.example.tigregym.domain.entities.Usuario;
import com.example.tigregym.domain.repository.EmpresaRepository;
import com.example.tigregym.domain.repository.UsuarioRepository;
import com.example.tigregym.domain.valueobject.CPF;
import com.example.tigregym.domain.valueobject.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UsuarioServicePort {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request) {

        Email email = new Email(request.email());

        if (usuarioRepository.existsUsuarioByEmailAndSenha(email, request.senha())) {

            Usuario usuario = usuarioRepository
                    .findByEmailAndSenha(email, request.senha())
                    .orElse(null);

            var token = tokenService.gerarToken(usuario);
            return new LoginResponse(token);
        }

        return null;
    }

    @Override
    public List<UsuarioResponse> listarTodosUsuariosTable() {

        Empresa empresa = getEmpresaUsuarioLogado();

        return usuarioRepository
                .findByEmpresa_IdAndStatusNot(
                        empresa.getId(),
                        EnumStatusUsuario.EXCLUIDO
                )
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    @Override
    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if (!criarAdminRequest.secretKey().equals(secret)) {
            throw new IllegalArgumentException("Chave inválida!");
        }

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(criarAdminRequest.razaoSocial());
        empresa.setNomeFantasia(criarAdminRequest.nomeFantasia());
        empresa.setCnpj(criarAdminRequest.cnpj());
        empresaRepository.save(empresa);

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioAdminSalvar.setEmpresa(empresa);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(
                usuarioAdminSalvar.getId(),
                "Usuário salvo com sucesso!"
        );
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Empresa getEmpresaUsuarioLogado() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            throw new IllegalStateException("Usuário não possui empresa vinculada!");
        }

        return empresaRepository.findById(usuarioLogado.empresa_id())
                .orElseThrow(() ->
                        new IllegalStateException("Empresa do usuário não encontrada!"));
    }

    @Override
    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        Empresa empresa = getEmpresaUsuarioLogado();

        if (id == 0L) {
            Usuario usuarioBanco = new Usuario(usuario);
            usuarioBanco.setEmpresa(empresa);
            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        }

        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusUsuario.EXCLUIDO
                )
                .orElse(null);

        if (usuarioBanco != null) {
            usuarioBanco.setNome(usuario.nome());
            usuarioBanco.setCpf(new CPF(usuario.cpf()));
            usuarioBanco.setEmail(new Email(usuario.email()));
            usuarioBanco.setSenha(usuario.senha());

            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        }

        return null;
    }

    @Override
    public UsuarioResponse buscarPorId(Long id) {

        Empresa empresa = getEmpresaUsuarioLogado();

        Usuario usuario = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusUsuario.EXCLUIDO
                )
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    @Override
    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Empresa empresa = getEmpresaUsuarioLogado();

        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusUsuario.EXCLUIDO
                )
                .orElse(null);

        if (usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }

        return false;
    }

    @Override
    public UsuarioResponse BuscarUsuariologado() {

        try {
            var usuarioLogado = getUsuarioLogado();
            Empresa empresa = getEmpresaUsuarioLogado();

            return usuarioRepository
                    .findByIdAndEmpresa_IdAndStatusNot(
                            usuarioLogado.id(),
                            empresa.getId(),
                            EnumStatusUsuario.EXCLUIDO
                    )
                    .stream()
                    .map(UsuarioResponse::new)
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário logado");
        }
    }
}
