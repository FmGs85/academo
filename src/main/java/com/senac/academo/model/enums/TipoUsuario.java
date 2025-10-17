package com.senac.academo.model.enums;

public enum TipoUsuario {
    ALUNO("aluno", "Aluno"),
    PROFESSOR("professor", "Professor"),
    ADMIN("admin", "Administrador");

    private final String valor;
    private final String descricao;

    TipoUsuario(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoUsuario fromValor(String valor) {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de usuário inválido: " + valor);
    }
}