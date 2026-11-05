package com.example.tigregym.domain.repository;

import com.example.tigregym.domain.entities.EnumStatusUsuario;
import com.example.tigregym.domain.entities.Usuario;
import com.example.tigregym.domain.valueobject.CPF;
import com.example.tigregym.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByEmailAndSenha(Email email, String senha);

    Optional<Usuario> findByEmailAndSenha(Email email, String senha);

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    boolean existsUsuarioByEmail(Email email);

    Optional<Usuario> findByEmailAndCpf(Email email, CPF cpf);

    List<Usuario> getUsuariosByEmpresa_Id(Long empresa);

    List<Usuario> findByEmpresa_IdAndStatusNot(Long empresaId, EnumStatusUsuario status);

    Optional<Usuario> findByIdAndEmpresa_IdAndStatusNot(
            Long id,
            Long empresaId,
            EnumStatusUsuario status
    );
}
