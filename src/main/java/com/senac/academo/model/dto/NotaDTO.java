package com.senac.academo.model.dto;

import java.time.LocalDateTime;

public class NotaDTO {

    private Integer id;
    private Integer matriculaId;
    private Integer avaliacaoId;
    private Double valor; // ← Nome correto do campo
    private LocalDateTime dataLancamento;
    private String observacao;

    // Campos adicionais para facilitar visualização
    private Integer alunoId;
    private String alunoNome;
    private Integer disciplinaId;
    private String disciplinaNome;
    private String avaliacaoTitulo;
    private String avaliacaoTipo;

    // Construtores
    public NotaDTO() {
    }

    public NotaDTO(Integer id, Double valor) {
        this.id = id;
        this.valor = valor;
    }

    // Getters e Setters
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public String getAvaliacaoTitulo() {
        return avaliacaoTitulo;
    }

    public void setAvaliacaoTitulo(String avaliacaoTitulo) {
        this.avaliacaoTitulo = avaliacaoTitulo;
    }

    public String getAvaliacaoTipo() {
        return avaliacaoTipo;
    }

    public void setAvaliacaoTipo(String avaliacaoTipo) {
        this.avaliacaoTipo = avaliacaoTipo;
    }

    @Override
    public String toString() {
        return "NotaDTO{" +
                "id=" + id +
                ", alunoNome='" + alunoNome + '\'' +
                ", disciplinaNome='" + disciplinaNome + '\'' +
                ", avaliacaoTitulo='" + avaliacaoTitulo + '\'' +
                ", valor=" + valor +
                '}';
    }
}