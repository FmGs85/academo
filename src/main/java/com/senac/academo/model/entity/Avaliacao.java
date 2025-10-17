package com.senac.academo.model.entity;

import com.senac.academo.model.enums.TipoAvaliacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacoes", indexes = {
        @Index(name = "idx_disciplina", columnList = "disciplina_id"),
        @Index(name = "idx_data_avaliacao", columnList = "data_avaliacao")
})
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @NotBlank(message = "Título da avaliação é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Tipo de avaliação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAvaliacao tipo;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.00", message = "Peso deve ser maior ou igual a 0")
    @DecimalMax(value = "1.00", message = "Peso deve ser menor ou igual a 1")
    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal peso = BigDecimal.ONE;

    @NotNull(message = "Data da avaliação é obrigatória")
    @Column(name = "data_avaliacao", nullable = false)
    private LocalDate dataAvaliacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Relacionamentos
    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.peso == null) {
            this.peso = BigDecimal.ONE;
        }
    }


    public Avaliacao() {
    }

    public Avaliacao(Disciplina disciplina, String titulo, TipoAvaliacao tipo,
                     BigDecimal peso, LocalDate dataAvaliacao) {
        this.disciplina = disciplina;
        this.titulo = titulo;
        this.tipo = tipo;
        this.peso = peso;
        this.dataAvaliacao = dataAvaliacao;
    }

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoAvaliacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoAvaliacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipo=" + tipo +
                ", peso=" + peso +
                ", dataAvaliacao=" + dataAvaliacao +
                '}';
    }
}