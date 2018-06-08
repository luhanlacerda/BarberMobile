package com.github.mavbraz.barbermobile.model.basicas;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.github.mavbraz.barbermobile.utils.BarberUtil;

import java.security.NoSuchAlgorithmException;

public class Cliente implements Parcelable {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String token;

    public Cliente() {
    }

    public Cliente(int id, String nome, String cpf, String email, String senha, String token) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.token = token;
    }

    private Cliente(Parcel from) {
        id = from.readInt();
        nome = from.readString();
        cpf = from.readString();
        email = from.readString();
        senha = from.readString();
        token = from.readString();
    }

    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(cpf);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(token);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf.replace(".", "").replace("-", "");
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

    @SuppressWarnings("EmptyCatchBlock")
    public void setSenha(String senha) {
        try {
            if (senha != null && !senha.trim().isEmpty()) {
                this.senha = BarberUtil.hash256(senha);
            }
        } catch (NoSuchAlgorithmException ex) {
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
