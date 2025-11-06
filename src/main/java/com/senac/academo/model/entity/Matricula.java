package com.senac.academo.model.entity;
import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.model.enums.SituacaoMatricula;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matricula",
        indexes = {
                @Index(name = "idx_matricula_aluno", columnList = "matricula_aluno_id"),
                @Index(name = "idx_matricula_disciplina", columnList = "matricula_disciplina_id"),
                @Index(name = "idx_matricula_status", columnList = "matricula_status")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_aluno_disciplina", columnNames = {"matricula_aluno_id", "matricula_disciplina_id"})
        }
)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_id")
    private Integer id;

    @NotNull(message = "Aluno é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_aluno_id", nullable = false)
    private Usuario aluno;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(name = "matricula_data", nullable = false, updatable = false)
    private LocalDateTime dataMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "matricula_status", nullable = false, length = 20)
    private StatusMatricula status = StatusMatricula.ATIVA;

    @Column(name = "matricula_media_final")
    private Double mediaFinal = 0.0;

    @Column(name = "matricula_carga_horaria_presente")
    private Integer cargaHorariaPresente = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "matricula_situacao", length = 20)
    private SituacaoMatricula situacao = SituacaoMatricula.CURSANDO;

    // Relacionamentos
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presenca> presencas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataMatricula = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusMatricula.ATIVA;
        }
        if (this.situacao == null) {
            this.situacao = SituacaoMatricula.CURSANDO;
        }
        if (this.mediaFinal == null) {
            this.mediaFinal = 0.0;
        }
        if (this.cargaHorariaPresente == null) {
            this.cargaHorariaPresente = 0;
        }
    }

    // Construtores
    public Matricula() {
    }

    public Matricula(Usuario aluno, Disciplina disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    // Getters e Setters
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

    public Double getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(Double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public Integer getCargaHorariaPresente() {
        return cargaHorariaPresente;
    }

    public void setCargaHorariaPresente(Integer cargaHorariaPresente) {
        this.cargaHorariaPresente = cargaHorariaPresente;
    }

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }

    // Métodos auxiliares
    public void calcularMediaFinal() {
        if (notas == null || notas.isEmpty()) {
            this.mediaFinal = 0.0;
            return;
        }

        double somaNotas = 0.0;
        double somaPesos = 0.0;

        for (Nota nota : notas) {
            if (nota.getAvaliacao() != null) {
                somaNotas += nota.getValor() * nota.getAvaliacao().getPeso();
                somaPesos += nota.getAvaliacao().getPeso();
            }
        }

        this.mediaFinal = somaPesos > 0 ? somaNotas / somaPesos : 0.0;
    }

    public void atualizarSituacao() {
        if (this.mediaFinal >= 7.0) {
            this.situacao = SituacaoMatricula.APROVADO;
        } else if (this.mediaFinal >= 4.0) {
            this.situacao = SituacaoMatricula.CURSANDO;
        } else {
            this.situacao = SituacaoMatricula.REPROVADO;
        }
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", aluno=" + (aluno != null ? aluno.getNome() : null) +
                ", disciplina=" + (disciplina != null ? disciplina.getNome() : null) +
                ", status=" + status +
                ", mediaFinal=" + mediaFinal +
                ", situacao=" + situacao +
                '}';
    }
}

