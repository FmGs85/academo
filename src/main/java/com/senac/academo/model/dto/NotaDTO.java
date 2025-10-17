package com.senac.academo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotaDTO {

    private Integer id;

    @NotNull(message = "Matrícula é obrigatória")
    private Integer matriculaId;

    @NotNull(message = "Avaliação é obrigatória")
    private Integer avaliacaoId;

    @NotNull(message = "Nota é obrigatória")
    @DecimalMin(value = "0.00", message = "Nota deve ser maior ou igual a 0")
    @DecimalMax(value = "10.00", message = "Nota deve ser menor ou igual a 10")
    private BigDecimal nota;

    private String observacoes;
    private LocalDateTime dataLancamento;


    private String nomeAluno;
    private String nomeDisciplina;
    private String tituloAvaliacao;
    private String tipoAvaliacao;
    private BigDecimal pesoAvaliacao;


    public NotaDTO() {
    }

    public NotaDTO(Integer id, Integer matriculaId, Integer avaliacaoId,
                   BigDecimal nota, String observacoes, LocalDateTime dataLancamento) {
        this.id = id;
        this.matriculaId = matriculaId;
        this.avaliacaoId = avaliacaoId;
        this.nota = nota;
        this.observacoes = observacoes;
        this.dataLancamento = dataLancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Integer matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Integer getAvaliacaoId() {
        return avaliacaoId;
    }

    public void setAvaliacaoId(Integer avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getTituloAvaliacao() {
        return tituloAvaliacao;
    }

    public void setTituloAvaliacao(String tituloAvaliacao) {
        this.tituloAvaliacao = tituloAvaliacao;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public BigDecimal getPesoAvaliacao() {
        return pesoAvaliacao;
    }

    public void setPesoAvaliacao(BigDecimal pesoAvaliacao) {
        this.pesoAvaliacao = pesoAvaliacao;
    }

}