package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "nota",
        indexes = {
                @Index(name = "idx_nota_matricula", columnList = "nota_matricula_id"),
                @Index(name = "idx_nota_avaliacao", columnList = "nota_avaliacao_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_matricula_avaliacao", columnNames = {"nota_matricula_id", "nota_avaliacao_id"})
        }
)
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_id")
    private Integer id;

    @NotNull(message = "Matrícula é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_matricula_id", nullable = false)
    private Matricula matricula;

    @NotNull(message = "Avaliação é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @NotNull(message = "Valor da nota é obrigatório")
    @Column(name = "nota_valor", nullable = false)
    private Double valor = 0.0;

    @Column(name = "nota_data_lancamento", nullable = false, updatable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "nota_observacao", columnDefinition = "TEXT")
    private String observacao;

    @PrePersist
    protected void onCreate() {
        this.dataLancamento = LocalDateTime.now();
        if (this.valor == null) {
            this.valor = 0.0;
        }
    }

    // Construtores
    public Nota() {
    }

    public Nota(Matricula matricula, Avaliacao avaliacao, Double valor) {
        this.matricula = matricula;
        this.avaliacao = avaliacao;
        this.valor = valor;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", matricula=" + (matricula != null ? matricula.getId() : null) +
                ", avaliacao=" + (avaliacao != null ? avaliacao.getTitulo() : null) +
                ", valor=" + valor +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}