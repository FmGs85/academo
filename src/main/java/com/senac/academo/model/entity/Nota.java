package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas",
        uniqueConstraints = @UniqueConstraint(name = "unique_matricula_avaliacao",
                columnNames = {"matricula_id", "avaliacao_id"}),
        indexes = {
                @Index(name = "idx_matricula", columnList = "matricula_id"),
                @Index(name = "idx_avaliacao", columnList = "avaliacao_id")
        })
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Matrícula é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @NotNull(message = "Avaliação é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @NotNull(message = "Nota é obrigatória")
    @DecimalMin(value = "0.00", message = "Nota deve ser maior ou igual a 0")
    @DecimalMax(value = "10.00", message = "Nota deve ser menor ou igual a 10")
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDateTime dataLancamento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    protected void onCreate() {
        this.dataLancamento = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataLancamento = LocalDateTime.now();
    }

    public Nota() {
    }

    public Nota(Matricula matricula, Avaliacao avaliacao, BigDecimal nota) {
        this.matricula = matricula;
        this.avaliacao = avaliacao;
        this.nota = nota;
    }

    public Nota(Matricula matricula, Avaliacao avaliacao, BigDecimal nota, String observacoes) {
        this.matricula = matricula;
        this.avaliacao = avaliacao;
        this.nota = nota;
        this.observacoes = observacoes;
    }

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

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }


    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", nota=" + nota +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}