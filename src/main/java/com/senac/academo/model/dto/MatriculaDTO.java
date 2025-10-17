package com.senac.academo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senac.academo.model.enums.StatusMatricula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

    private Integer id;

    @NotNull(message = "Aluno é obrigatório")
    private Integer alunoId;

    @NotNull(message = "Disciplina é obrigatória")
    private Integer disciplinaId;

    private LocalDateTime dataMatricula;

    @NotNull(message = "Status é obrigatório")
    private StatusMatricula status;

    @NotBlank(message = "Período é obrigatório")
    private String periodo;


    private String nomeAluno;
    private String emailAluno;
    private String nomeDisciplina;
    private String codigoDisciplina;
    private BigDecimal mediaPonderada;
    private String situacao; // Aprovado, Recuperação, Reprovado
    private Integer totalAvaliacoes;
    private Integer avaliacoesRealizadas;


    public MatriculaDTO() {
    }

    public MatriculaDTO(Integer id, Integer alunoId, Integer disciplinaId,
                        LocalDateTime dataMatricula, StatusMatricula status, String periodo) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.dataMatricula = dataMatricula;
        this.status = status;
        this.periodo = periodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Integer disciplinaId) {
        this.disciplinaId = disciplinaId;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
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

    public BigDecimal getMediaPonderada() {
        return mediaPonderada;
    }

    public void setMediaPonderada(BigDecimal mediaPonderada) {
        this.mediaPonderada = mediaPonderada;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public Integer getAvaliacoesRealizadas() {
        return avaliacoesRealizadas;
    }

    public void setAvaliacoesRealizadas(Integer avaliacoesRealizadas) {
        this.avaliacoesRealizadas = avaliacoesRealizadas;
    }

}