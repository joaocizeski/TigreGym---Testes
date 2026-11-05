package com.example.tigregym.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    public Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    public Plano plano;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public LocalDateTime dataInicio;

    public LocalDateTime dataVencimento;

    public EnumStatusMatricula status = EnumStatusMatricula.ATIVO;
}
