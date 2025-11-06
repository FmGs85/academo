package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "disciplina_professor",
        indexes = {
                @Index(name = "idx_dp_disciplina", columnList = "disciplina_professor_disciplina_id"),
                @Index(name = "idx_dp_professor", columnList = "disciplina_professor_professor_id"),
                @Index(name = "idx_dp_semestre", columnList = "disciplina_professor_semestre, disciplina_professor_ano")
        }
)
public class DisciplinaProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_professor_id")
    private Integer id;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_professor_disciplina_id", nullable = false)
    private Disciplina disciplina;

    @NotNull(message = "Professor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_professor_professor_id", nullable = false)
    private Usuario professor;

    @NotNull(message = "Semestre é obrigatório")
    @Column(name = "disciplina_professor_semestre", nullable = false, length = 20)
    private String semestre;

    @NotNull(message = "Ano é obrigatório")
    @Column(name = "disciplina_professor_ano", nullable = false)
    private Integer ano;

    @Column(name = "disciplina_professor_turma", length = 10)
    private String turma;

    @Column(name = "disciplina_professor_horario", length = 100)
    private String horario;

    @Column(name = "disciplina_professor_sala", length = 50)
    private String sala;

    @Column(name = "disciplina_professor_status", nullable = false)
    private Boolean ativo = true;

    // Construtores
    public DisciplinaProfessor() {
    }

    public DisciplinaProfessor(Disciplina disciplina, Usuario professor, String semestre, Integer ano) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.ano = ano;
    }

    @PrePersist
    protected void onCreate() {
        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
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
                ", disciplina=" + (disciplina != null ? disciplina.getNome() : null) +
                ", professor=" + (professor != null ? professor.getNome() : null) +
                ", semestre='" + semestre + '\'' +
                ", ano=" + ano +
                ", turma='" + turma + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}