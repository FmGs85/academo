package com.senac.academo.model.enums;

public enum TipoAvaliacao {
    PROVA("prova", "Prova"),
    TRABALHO("trabalho", "Trabalho"),
    PROJETO("projeto", "Projeto"),
    PARTICIPACAO("participacao", "Participação");

    private final String valor;
    private final String descricao;

    TipoAvaliacao(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAvaliacao fromValor(String valor) {
        for (TipoAvaliacao tipo : TipoAvaliacao.values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de avaliação inválido: " + valor);
    }
}