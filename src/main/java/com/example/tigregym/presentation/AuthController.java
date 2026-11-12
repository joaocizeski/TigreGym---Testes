package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.LoginRequest;
import com.example.tigregym.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação",
        description = "Grupo de APIs responsável pela autenticação de usuários do sistema!")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de usuário")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        var resultadoAutenticacaoRetornoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(request);

        // Confere se existe um usuário com o e-mail e a senha informados
        if (resultadoAutenticacaoRetornoToken != null) {
            return ResponseEntity.ok(resultadoAutenticacaoRetornoToken);
        }
        return ResponseEntity.badRequest().body("Usuário ou senha Invalido!");
    }
/*
    @PostMapping("/cadastro")
    @Operation(
            summary = "Método de cadastro de usuário!",
            description = "Método responsável por efetuar o cadastro de um novo usuário no sistema"
    )
    public ResponseEntity<?> cadastro(@RequestBody CadastroRequest request) {

        // Verifica se o email já está cadastrado
        if (usuarioRepository.existsUsuarioByEmail(request.email())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        // Cria um novo usuário
        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setCpf(request.cpf());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());

        // Salva o usuário no banco de dados
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/recuperar-senha")
    @Operation(
            summary = "Método de recuperação de senha!",
            description = "Método responsável por recuperar a senha de um usuário"
    )
    public ResponseEntity<?> recuperarSenha(@RequestBody RecuperarSenhaRequest request) {

        // Procura o usuário pelo e-mail e CPF
        Usuario usuarioBanco = usuarioRepository
                .findByEmailAndCpf(request.email(), request.cpf())
                .orElse(null);

        if (usuarioBanco != null) {

            // Verifica se a nova senha é diferente da senha atual
            if (usuarioBanco.getSenha().equals(request.novaSenha())) {
                return ResponseEntity.badRequest()
                        .body("A nova senha não pode ser igual à senha atual!");
            }

            // Altera a senha do usuário
            usuarioBanco.setSenha(request.novaSenha());

            usuarioRepository.save(usuarioBanco);

            return ResponseEntity.ok("Senha alterada com sucesso!");
        }

        return ResponseEntity.badRequest().body("E-mail ou CPF inválido!");
    }
    */
}