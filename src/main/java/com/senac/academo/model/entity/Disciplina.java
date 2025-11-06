package com.senac.academo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplina", indexes = {
        @Index(name = "idx_disciplina_codigo", columnList = "disciplina_codigo"),
        @Index(name = "idx_disciplina_status", columnList = "disciplina_status")
})
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_id")
    private Integer id;

    @NotBlank(message = "Código é obrigatório")
    @Column(name = "disciplina_codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "disciplina_nome", nullable = false)
    private String nome;

    @Column(name = "disciplina_descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Carga horária é obrigatória")
    @Column(name = "disciplina_carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @NotNull(message = "Créditos são obrigatórios")
    @Column(name = "disciplina_creditos", nullable = false)
    private Integer creditos;

    @Column(name = "disciplina_status", nullable = false)
    private Boolean ativo = true;

    @Column(name = "disciplina_data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Relacionamentos
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisciplinaProfessor> professores = new ArrayList<>();

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    // Construtores
    public Disciplina() {
    }

    public Disciplina(String codigo, String nome, Integer cargaHoraria, Integer creditos) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.creditos = creditos;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<DisciplinaProfessor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<DisciplinaProfessor> professores) {
        this.professores = professores;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", creditos=" + creditos +
                ", ativo=" + ativo +
                '}';
    }
}