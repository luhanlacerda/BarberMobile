package com.example.vitoroliveira.barber.pessoa;

public abstract class User extends Pessoa {

    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
