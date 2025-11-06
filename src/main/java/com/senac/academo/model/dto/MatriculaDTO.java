package com.senac.academo.model.dto;

import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.model.enums.SituacaoMatricula;

import java.time.LocalDateTime;

public class MatriculaDTO {

    private Integer id;
    private Integer alunoId;
    private String alunoNome;
    private Integer disciplinaId;
    private String disciplinaNome;
    private LocalDateTime dataMatricula;
    private StatusMatricula status;
    private SituacaoMatricula situacao;
    private Double mediaFinal;
    private Integer cargaHorariaPresente;

    // Construtores
    public MatriculaDTO() {
    }

    public MatriculaDTO(Integer id, Integer alunoId, String alunoNome, Integer disciplinaId,
                        String disciplinaNome, LocalDateTime dataMatricula, StatusMatricula status,
                        SituacaoMatricula situacao, Double mediaFinal, Integer cargaHorariaPresente) {
        this.id = id;
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.disciplinaId = disciplinaId;
        this.disciplinaNome = disciplinaNome;
        this.dataMatricula = dataMatricula;
        this.status = status;
        this.situacao = situacao;
        this.mediaFinal = mediaFinal;
        this.cargaHorariaPresente = cargaHorariaPresente;
    }

    // Getters e Setters
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

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public Integer getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Integer disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }

    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
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

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
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
}