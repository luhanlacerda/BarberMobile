package com.github.mavbraz.barbermobile.model.basicas;

import android.os.Parcel;
import android.os.Parcelable;

public class Servico implements Parcelable {
    private int id;
    private String nome;
    private String descricao;
    private double valor;

    public Servico() {
    }

    public Servico(int id, String nome, String descricao, double valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    private Servico(Parcel from) {
        id = from.readInt();
        nome = from.readString();
        descricao = from.readString();
        valor = from.readDouble();
    }

    public static final Parcelable.Creator<Servico> CREATOR = new Parcelable.Creator<Servico>() {
        @Override
        public Servico createFromParcel(Parcel in) {
            return new Servico(in);
        }

        @Override
        public Servico[] newArray(int size) {
            return new Servico[size];
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
        dest.writeString(descricao);
        dest.writeDouble(valor);
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }

    public void setValor(double valor) { this.valor = valor; }

}
