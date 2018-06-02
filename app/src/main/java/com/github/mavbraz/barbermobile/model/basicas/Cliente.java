package com.github.mavbraz.barbermobile.model.basicas;

import com.github.mavbraz.barbermobile.utils.BarberUtil;

import java.security.NoSuchAlgorithmException;

public class Cliente {

    private int id;
    private String email;
    private String senha;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws NoSuchAlgorithmException {
        this.senha = BarberUtil.hash256(senha);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
