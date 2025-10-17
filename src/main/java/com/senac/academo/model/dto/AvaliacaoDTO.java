package com.senac.academo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senac.academo.model.enums.TipoAvaliacao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoDTO {

    private Integer id;

    @NotNull(message = "Disciplina é obrigatória")
    private Integer disciplinaId;

    @NotBlank(message = "Título da avaliação é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "Tipo de avaliação é obrigatório")
    private TipoAvaliacao tipo;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.00", message = "Peso deve ser maior ou igual a 0")
    @DecimalMax(value = "1.00", message = "Peso deve ser menor ou igual a 1")
    private BigDecimal peso;

    @NotNull(message = "Data da avaliação é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAvaliacao;

    private LocalDateTime dataCriacao;


    private String nomeDisciplina;
    private String codigoDisciplina;
    private Long totalNotasLancadas;
    private Long totalAlunosMatriculados;
    private BigDecimal mediaGeral;
    private String statusAvaliacao; // Futura, Em andamento, Realizada


    public AvaliacaoDTO() {
    }

    public AvaliacaoDTO(Integer id, Integer disciplinaId, String titulo, String descricao,
                        TipoAvaliacao tipo, BigDecimal peso, LocalDate dataAvaliacao) {
        this.id = id;
        this.disciplinaId = disciplinaId;
        this.titulo = titulo;
        this.descricao = descricao;
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

    public Integer getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Integer disciplinaId) {
        this.disciplinaId = disciplinaId;
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

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public Long getTotalNotasLancadas() {
        return totalNotasLancadas;
    }

    public void setTotalNotasLancadas(Long totalNotasLancadas) {
        this.totalNotasLancadas = totalNotasLancadas;
    }

    public Long getTotalAlunosMatriculados() {
        return totalAlunosMatriculados;
    }

    public void setTotalAlunosMatriculados(Long totalAlunosMatriculados) {
        this.totalAlunosMatriculados = totalAlunosMatriculados;
    }

    public BigDecimal getMediaGeral() {
        return mediaGeral;
    }

    public void setMediaGeral(BigDecimal mediaGeral) {
        this.mediaGeral = mediaGeral;
    }

    public String getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public void setStatusAvaliacao(String statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
    }

}