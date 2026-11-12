package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.AtualizarStatusUsuarioRequest;
import com.example.tigregym.application.DTOs.CriarAdminRequest;
import com.example.tigregym.application.DTOs.CriarAdminResponse;
import com.example.tigregym.application.DTOs.UsuarioRequest;
import com.example.tigregym.application.DTOs.UsuarioResponse;
import com.example.tigregym.application.services.UsuarioService;
import com.example.tigregym.domain.entities.EnumStatusUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários",
        description = "Grupo de APIs responsável por controlar a estrutura de usuários do sistema!")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Método de consulta da lista de usuários!",
            description = "Método responsável por consultar os usuários da empresa do usuário logado")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    @PostMapping("/admin")
    public ResponseEntity<?> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest) {
        try {
            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(respostaSalvar);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Método de consulta de usuário por ID!",
            description = "Método responsável por efetuar a consulta de um usuário através do ID"
    )
    public ResponseEntity<UsuarioResponse> listarPorId(@PathVariable Long id) {

        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);

        if (usuarioBanco != null) {
            return ResponseEntity.ok(usuarioBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuário!",
            description = "Método responsável por efetuar a criação de novos usuários!")
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest usuario) {
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario, 0L);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Método de atualização do status do usuário!",
            description = "Método responsável por alterar o status de um usuário através do ID"
    )
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusUsuarioRequest statusRequest) {

        boolean atualizado = usuarioService.atualizarStatus(statusRequest.status(), id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Método de atualização de usuário!",
            description = "Método responsável por atualizar os dados de um usuário através do ID"
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequest usuario) {
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario, id);

            if (usuarioBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(
            summary = "Método de exclusão de usuário!",
            description = "Método responsável por alterar o status do usuário para EXCLUIDO através do ID"
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean atualizado = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO, id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscarusuariologado")
    // @Operation(summary = "Usuario Logado", description = "Usuario Logado");
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(){
        return ResponseEntity.ok(usuarioService.BuscarUsuariologado());
    }

}
