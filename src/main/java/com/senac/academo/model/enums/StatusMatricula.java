package com.senac.academo.model.enums;

public enum StatusMatricula {
    ATIVA("ativa", "Ativa"),
    TRANCADA("trancada", "Trancada"),
    CONCLUIDA("concluida", "Concluída"),
    REPROVADA("reprovada", "Reprovada");

    private final String valor;
    private final String descricao;

    StatusMatricula(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusMatricula fromValor(String valor) {
        for (StatusMatricula status : StatusMatricula.values()) {
            if (status.valor.equalsIgnoreCase(valor)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de matrícula inválido: " + valor);
    }
}