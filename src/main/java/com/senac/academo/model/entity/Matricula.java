package com.senac.academo.model.entity;

import com.senac.academo.model.enums.StatusMatricula;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matriculas",
        uniqueConstraints = @UniqueConstraint(name = "unique_aluno_disc_periodo",
                columnNames = {"aluno_id", "disciplina_id", "periodo"}),
        indexes = {
                @Index(name = "idx_aluno", columnList = "aluno_id"),
                @Index(name = "idx_disciplina", columnList = "disciplina_id"),
                @Index(name = "idx_periodo", columnList = "periodo")
        })
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Aluno é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(name = "data_matricula", nullable = false, updatable = false)
    private LocalDateTime dataMatricula;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMatricula status = StatusMatricula.ATIVA;

    @NotBlank(message = "Período é obrigatório")
    @Column(nullable = false, length = 10)
    private String periodo;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataMatricula = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusMatricula.ATIVA;
        }
    }


    public Matricula() {
    }

    public Matricula(Usuario aluno, Disciplina disciplina, String periodo) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.periodo = periodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", alunoId=" + (aluno != null ? aluno.getId() : null) +
                ", disciplinaId=" + (disciplina != null ? disciplina.getId() : null) +
                ", periodo='" + periodo + '\'' +
                ", status=" + status +
                '}';
    }
}