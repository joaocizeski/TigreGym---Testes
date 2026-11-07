package com.example.tigregym.domain.repository;

import com.example.tigregym.domain.entities.Aluno;
import com.example.tigregym.domain.entities.EnumStatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<List<Aluno>> findByStatusNot(EnumStatusAluno status);

    List<Aluno> findByEmpresa_IdAndStatusNot(Long empresaId, EnumStatusAluno status);

    List<Aluno> findByEmpresa_IdOrderByStatusAsc(Long empresaId);

    Optional<Aluno> findByIdAndEmpresa_IdAndStatusNot(
            Long id,
            Long empresaId,
            EnumStatusAluno status
    );
}
