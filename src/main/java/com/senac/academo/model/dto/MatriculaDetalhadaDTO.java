package com.senac.academo.model.dto;

import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.model.enums.SituacaoMatricula;

import java.time.LocalDateTime;

public class MatriculaDetalhadaDTO {

    private Integer id;
    private DisciplinaInfo disciplina;
    private LocalDateTime dataMatricula;
    private StatusMatricula status;
    private SituacaoMatricula situacao;
    private Double mediaFinal;
    private Integer cargaHorariaPresente;

    // Classe interna para informações da disciplina
    public static class DisciplinaInfo {
        private Integer id;
        private String codigo;
        private String nome;
        private String descricao;
        private Integer cargaHoraria;
        private Integer creditos;
        private ProfessorInfo professor;

        // Construtores
        public DisciplinaInfo() {}

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

        public ProfessorInfo getProfessor() {
            return professor;
        }

        public void setProfessor(ProfessorInfo professor) {
            this.professor = professor;
        }
    }

    // Classe interna para informações do professor
    public static class ProfessorInfo {
        private Integer id;
        private String nome;
        private String email;

        // Construtores
        public ProfessorInfo() {}

        // Getters e Setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // Construtores
    public MatriculaDetalhadaDTO() {
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DisciplinaInfo getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaInfo disciplina) {
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