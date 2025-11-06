package com.senac.academo.model.entity;
import com.senac.academo.model.enums.TipoAvaliacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacao",
        indexes = {
                @Index(name = "idx_avaliacao_disciplina", columnList = "avaliacao_disciplina_id"),
                @Index(name = "idx_avaliacao_data", columnList = "avaliacao_data"),
                @Index(name = "idx_avaliacao_tipo", columnList = "avaliacao_tipo")
        }
)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id")
    private Integer id;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_disciplina_id", nullable = false)
    private Disciplina disciplina;

    @NotBlank(message = "Título é obrigatório")
    @Column(name = "avaliacao_titulo", nullable = false)
    private String titulo;

    @Column(name = "avaliacao_descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "avaliacao_tipo", nullable = false, length = 20)
    private TipoAvaliacao tipo;

    @NotNull(message = "Data é obrigatória")
    @Column(name = "avaliacao_data", nullable = false)
    private LocalDate data;

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "avaliacao_valor", nullable = false)
    private Double valor;

    @Column(name = "avaliacao_peso")
    private Double peso = 1.0;

    @Column(name = "avaliacao_status", nullable = false)
    private Boolean ativo = true;

    // Relacionamentos
    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.ativo == null) {
            this.ativo = true;
        }
        if (this.peso == null) {
            this.peso = 1.0;
        }
    }

    // Construtores
    public Avaliacao() {
    }

    public Avaliacao(Disciplina disciplina, String titulo, TipoAvaliacao tipo, LocalDate data, Double valor) {
        this.disciplina = disciplina;
        this.titulo = titulo;
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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
                ", data=" + data +
                ", valor=" + valor +
                ", peso=" + peso +
                ", ativo=" + ativo +
                '}';
    }
}

