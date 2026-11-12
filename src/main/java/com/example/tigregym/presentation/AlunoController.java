package com.example.tigregym.presentation;

import com.example.tigregym.application.DTOs.AlunoRequest;
import com.example.tigregym.application.DTOs.AlunoResponse;
import com.example.tigregym.application.DTOs.AtualizarStatusAlunoRequest;
import com.example.tigregym.application.services.AlunoService;
import com.example.tigregym.domain.entities.EnumStatusAluno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos",
        description = "Grupo de APIs responsável por controlar a estrutura de alunos do sistema!")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    @Operation(summary = "Método de consulta da lista de alunos!",
            description = "Método responsável por consultar os alunos da empresa do usuário logado")
    public ResponseEntity<List<AlunoResponse>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodosAlunoTable());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de aluno!",
            description = "Método responsável por efetuar a criação de novos alunos!")
    public ResponseEntity<?> criar(@RequestBody AlunoRequest aluno) {
        try {
            AlunoResponse alunoBanco = alunoService.salvarAluno(aluno, 0L);
            return ResponseEntity.ok(alunoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Método de consulta de aluno por ID!",
            description = "Método responsável por efetuar a consulta de um aluno através do ID"
    )
    public ResponseEntity<AlunoResponse> listarPorId(@PathVariable Long id) {

        AlunoResponse alunoBanco = alunoService.buscarPorId(id);

        if (alunoBanco != null) {
            return ResponseEntity.ok(alunoBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Método de atualização do status do aluno!",
            description = "Método responsável por alterar o status de um aluno através do ID"
    )
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusAlunoRequest statusRequest) {

        boolean atualizado = alunoService.atualizarStatus(statusRequest.status(), id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Método de atualização de aluno!",
            description = "Método responsável por atualizar os dados de um aluno através do ID"
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody AlunoRequest aluno) {
        try {
            AlunoResponse alunoBanco = alunoService.salvarAluno(aluno, id);

            if (alunoBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(alunoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(
            summary = "Método de exclusão de aluno!",
            description = "Método responsável por alterar o status do aluno para EXCLUIDO"
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean atualizado = alunoService.atualizarStatus(EnumStatusAluno.EXCLUIDO, id);

        if (atualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
