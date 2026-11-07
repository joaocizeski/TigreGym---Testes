package com.example.tigregym.domain.repository;

import com.example.tigregym.domain.entities.EnumStatusPlano;
import com.example.tigregym.domain.entities.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

    Optional<List<Plano>> findByStatusNot(EnumStatusPlano status);

    List<Plano> findByEmpresa_IdAndStatusNot(Long empresaId, EnumStatusPlano status);

    List<Plano> findByEmpresa_IdOrderByStatusAsc(Long empresaId);

    Optional<Plano> findByIdAndEmpresa_IdAndStatus(
            Long id,
            Long empresaId,
            EnumStatusPlano status
    );

    Optional<Plano> findByIdAndEmpresa_IdAndStatusNot(
            Long id,
            Long empresaId,
            EnumStatusPlano status
    );
}
