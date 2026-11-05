package com.example.tigregym.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String nome;

    public String descricao;

    public String valor;

    @Column(name = "duracao_meses")
    public Integer duracaoEmMeses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @OneToMany(mappedBy = "plano")
    private Set<Matricula> matriculas = new HashSet<>();

    public EnumStatusPlano status = EnumStatusPlano.ATIVO;

    public void setDuracaoEmMeses(Integer duracaoEmMeses) {
        if (duracaoEmMeses == null || duracaoEmMeses < 1) {
            throw new IllegalArgumentException("A duração do plano deve ser maior que zero!");
        }

        this.duracaoEmMeses = duracaoEmMeses;
    }
}
