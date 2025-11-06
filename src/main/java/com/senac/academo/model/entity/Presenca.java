package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "presenca",
        indexes = {
                @Index(name = "idx_presenca_matricula", columnList = "presenca_matricula_id"),
                @Index(name = "idx_presenca_data", columnList = "presenca_data")
        }
)
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presenca_id")
    private Integer id;

    @NotNull(message = "Matrícula é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presenca_matricula_id", nullable = false)
    private Matricula matricula;

    @NotNull(message = "Data da aula é obrigatória")
    @Column(name = "presenca_data", nullable = false)
    private LocalDate data;

    @NotNull(message = "Situação de presença é obrigatória")
    @Column(name = "presenca_presente", nullable = false)
    private Boolean presente = false;

    @Column(name = "presenca_carga_horaria", nullable = false)
    private Integer cargaHoraria = 2;

    @Column(name = "presenca_observacao", length = 255)
    private String observacao;

    @PrePersist
    protected void onCreate() {
        if (this.presente == null) {
            this.presente = false;
        }
        if (this.cargaHoraria == null) {
            this.cargaHoraria = 2;
        }
    }

    // Construtores
    public Presenca() {
    }

    public Presenca(Matricula matricula, LocalDate data, Boolean presente) {
        this.matricula = matricula;
        this.data = data;
        this.presente = presente;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Presenca{" +
                "id=" + id +
                ", matricula=" + (matricula != null ? matricula.getId() : null) +
                ", data=" + data +
                ", presente=" + presente +
                ", cargaHoraria=" + cargaHoraria +
                '}';
    }
}