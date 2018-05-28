package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.BarberHelper;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;

public class NegocioCliente implements INegocioCliente {

    private static String ERRO_NOME = "Favor informar o nome";
    private static String ERRO_DATA = "Data de nascimento inválida";
    private static String ERRO_RG = "RG inválido";
    private static String ERRO_CPF = "CPF inválido";
    private static String ERRO_SENHA = "Senha inválida";
    private static String ERRO_EMAIL = "E-mail inválido";
    private static String ERRO_ENDERECO = "Endereco inválido";
    private static String ERRO_ID = "ID inválido";

    private Context ctx;

    public NegocioCliente(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void insert(Cliente cliente) throws Exception {

        //new BarberHelper(ctx).insert(cliente);

    }

}
