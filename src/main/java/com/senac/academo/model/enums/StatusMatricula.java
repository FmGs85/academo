package com.senac.academo.model.enums;

public enum StatusMatricula {
    ATIVA("Ativa"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluída"),
    TRANCADA("Trancada");

    private final String descricao;

    StatusMatricula(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}