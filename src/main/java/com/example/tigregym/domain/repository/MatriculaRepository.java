package com.example.tigregym.domain.repository;

import com.example.tigregym.domain.entities.EnumStatusMatricula;
import com.example.tigregym.domain.entities.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Optional<List<Matricula>> findByStatusNot(EnumStatusMatricula status);

    List<Matricula> findByEmpresa_IdAndStatusNot(Long empresaId, EnumStatusMatricula status);

    List<Matricula> findByEmpresa_IdOrderByStatusAsc(Long empresaId);

    Optional<Matricula> findByIdAndEmpresa_IdAndStatusNot(
            Long id,
            Long empresaId,
            EnumStatusMatricula status
    );
}
