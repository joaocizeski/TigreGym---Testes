package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.AtualizarStatusMatriculaRequest;
import com.example.tigregym.application.DTOs.MatriculaRequest;
import com.example.tigregym.application.DTOs.MatriculaResponse;
import com.example.tigregym.application.ports.MatriculaServicePort;
import com.example.tigregym.domain.entities.EnumStatusMatricula;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matrículas",
        description = "Grupo de APIs responsável por controlar a estrutura de matrículas do sistema!")
public class MatriculaController {

    @Autowired
    private MatriculaServicePort matriculaService;

    @GetMapping
    @Operation(summary = "Método de consulta da lista de matrículas!",
            description = "Método responsável por consultar as matrículas da empresa do usuário logado")
    public ResponseEntity<List<MatriculaResponse>> listarTodos() {
        return ResponseEntity.ok(matriculaService.listarTodosMatriculaTable());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de matrícula!",
            description = "Método responsável por efetuar a criação de novas matrículas!")
    public ResponseEntity<?> criar(@RequestBody MatriculaRequest matricula) {
        try {
            MatriculaResponse matriculaBanco = matriculaService.salvarMatricula(matricula, 0L);

            if (matriculaBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(matriculaBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Método de consulta de matrícula por ID!",
            description = "Método responsável por efetuar a consulta de uma matrícula através do ID"
    )
    public ResponseEntity<MatriculaResponse> listarPorId(@PathVariable Long id) {

        MatriculaResponse matriculaBanco = matriculaService.buscarPorId(id);

        if (matriculaBanco != null) {
            return ResponseEntity.ok(matriculaBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Método de atualização do status da matrícula!",
            description = "Método responsável por alterar o status de uma matrícula através do ID"
    )
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusMatriculaRequest statusRequest) {

        boolean atualizado = matriculaService.atualizarStatus(statusRequest.status(), id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Método de atualização de matrícula!",
            description = "Método responsável por atualizar os dados de uma matrícula através do ID"
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody MatriculaRequest matricula) {
        try {
            MatriculaResponse matriculaBanco = matriculaService.salvarMatricula(matricula, id);

            if (matriculaBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(matriculaBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(
            summary = "Método de exclusão de matrícula!",
            description = "Método responsável por alterar o status da matrícula para EXCLUIDO através do ID"
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean atualizado = matriculaService.atualizarStatus(EnumStatusMatricula.EXCLUIDO, id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
