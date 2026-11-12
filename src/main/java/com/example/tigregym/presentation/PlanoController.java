package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.AtualizarStatusPlanoRequest;
import com.example.tigregym.application.DTOs.PlanoRequest;
import com.example.tigregym.application.DTOs.PlanoResponse;
import com.example.tigregym.application.services.PlanoService;
import com.example.tigregym.domain.entities.EnumStatusPlano;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@Tag(name = "Planos",
        description = "Grupo de APIs responsável por controlar a estrutura de planos do sistema!")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @GetMapping
    @Operation(summary = "Método de consulta da lista de planos!",
            description = "Método responsável por consultar os planos da empresa do usuário logado")
    public ResponseEntity<List<PlanoResponse>> listarTodos() {
        return ResponseEntity.ok(planoService.listarTodosPlanoTable());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de plano!",
            description = "Método responsável por efetuar a criação de novos planos!")
    public ResponseEntity<?> criar(@RequestBody PlanoRequest plano) {
        try {
            PlanoResponse planoBanco = planoService.salvarPlano(plano, 0L);
            return ResponseEntity.ok(planoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Método de consulta de plano por ID!",
            description = "Método responsável por efetuar a consulta de um plano através do ID"
    )
    public ResponseEntity<PlanoResponse> listarPorId(@PathVariable Long id) {

        PlanoResponse planoBanco = planoService.buscarPorId(id);

        if (planoBanco != null) {
            return ResponseEntity.ok(planoBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Método de atualização do status do plano!",
            description = "Método responsável por alterar o status de um plano através do ID"
    )
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusPlanoRequest statusRequest) {

        boolean atualizado = planoService.atualizarStatus(statusRequest.status(), id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Método de atualização de plano!",
            description = "Método responsável por atualizar os dados de um plano através do ID"
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody PlanoRequest plano) {
        try {
            PlanoResponse planoBanco = planoService.salvarPlano(plano, id);

            if (planoBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(planoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(
            summary = "Método de exclusão de plano!",
            description = "Método responsável por alterar o status do plano para EXCLUIDO através do ID"
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean atualizado = planoService.atualizarStatus(EnumStatusPlano.EXCLUIDO, id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
