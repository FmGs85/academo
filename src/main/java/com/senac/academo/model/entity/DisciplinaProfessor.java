package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "disciplinas_professor",
        uniqueConstraints = @UniqueConstraint(name = "unique_prof_disc",
                columnNames = {"professor_id", "disciplina_id"}),
        indexes = {
                @Index(name = "idx_professor", columnList = "professor_id"),
                @Index(name = "idx_disciplina", columnList = "disciplina_id")
        })
public class DisciplinaProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Professor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Usuario professor;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(name = "data_atribuicao", nullable = false, updatable = false)
    private LocalDateTime dataAtribuicao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @PrePersist
    protected void onCreate() {
        this.dataAtribuicao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }


    public DisciplinaProfessor() {
    }

    public DisciplinaProfessor(Usuario professor, Disciplina disciplina) {
        this.professor = professor;
        this.disciplina = disciplina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDateTime getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(LocalDateTime dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "DisciplinaProfessor{" +
                "id=" + id +
                ", professorId=" + (professor != null ? professor.getId() : null) +
                ", disciplinaId=" + (disciplina != null ? disciplina.getId() : null) +
                ", ativo=" + ativo +
                '}';
    }
}