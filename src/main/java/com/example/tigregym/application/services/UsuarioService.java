package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.CriarAdminRequest;
import com.example.tigregym.application.DTOs.CriarAdminResponse;
import com.example.tigregym.application.DTOs.LoginRequest;
import com.example.tigregym.application.DTOs.LoginResponse;
import com.example.tigregym.application.DTOs.UsuarioLogado;
import com.example.tigregym.application.DTOs.UsuarioRequest;
import com.example.tigregym.application.DTOs.UsuarioResponse;
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
public class UsuarioService {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TokenService tokenService;

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

    public List<UsuarioResponse> listarTodosUsuariosTable() {

        var usuarioLogado = getUsuarioLogado();

        return usuarioRepository
                .findByEmpresa_IdOrderByStatusAsc(usuarioLogado.empresa_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

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
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        var usuarioLogado = getUsuarioLogado();

        var empresaLogada = empresaRepository
                .findById(usuarioLogado.empresa_id())
                .orElse(null);

        if (empresaLogada == null) {
            throw new IllegalStateException("Empresa do usuário não encontrada!");
        }

        if (id == 0L) {
            Usuario usuarioBanco = new Usuario(usuario);
            usuarioBanco.setEmpresa(empresaLogada);
            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        }

        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
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

    public UsuarioResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        Usuario usuario = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
                        EnumStatusUsuario.EXCLUIDO
                )
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        var usuarioLogado = getUsuarioLogado();

        Usuario usuarioBanco = usuarioRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
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

    public UsuarioResponse BuscarUsuariologado() {

        try {
            var usuarioLogado = getUsuarioLogado();

            return usuarioRepository
                    .findByIdAndEmpresa_IdAndStatusNot(
                            usuarioLogado.id(),
                            usuarioLogado.empresa_id(),
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
