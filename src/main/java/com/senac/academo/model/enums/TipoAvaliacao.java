package com.senac.academo.model.enums;

public enum TipoAvaliacao {
    PROVA("Prova"),
    TRABALHO("Trabalho"),
    PROJETO("Projeto"),
    SEMINARIO("Seminário"),
    OUTROS("Outros");

    private final String descricao;

    TipoAvaliacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAvaliacao fromValor(String valor) {
        try {
            return TipoAvaliacao.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de avaliação inválido: " + valor);
        }
    }
}