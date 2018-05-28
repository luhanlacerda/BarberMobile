package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;

import java.util.List;

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

        if (cliente.getCpf().isEmpty()){
            throw new Exception(ERRO_CPF);
        }

        if (cliente.getRg().isEmpty()){
            throw new Exception(ERRO_RG);
        }

        if (cliente.getSenha().isEmpty()){
            throw new Exception(ERRO_SENHA);
        }

        if (cliente.getDataNascimento() == null){
            throw new Exception(ERRO_DATA);
        }

        if (cliente.getEmail().isEmpty()){
            throw new Exception(ERRO_EMAIL);
        }

        if (cliente.getEndereco().isEmpty()){
            throw new Exception(ERRO_ENDERECO);
        }

        if (cliente.getNome().isEmpty()){
            throw new Exception(ERRO_NOME);
        }

        new DAOCliente(ctx).insert(cliente);

    }

    @Override
    public void update(Cliente cliente) throws Exception {

        if (cliente.getCpf().isEmpty()){
            throw new Exception(ERRO_CPF);
        }

        if (cliente.getRg().isEmpty()){
            throw new Exception(ERRO_RG);
        }

        if (cliente.getSenha().isEmpty()){
            throw new Exception(ERRO_SENHA);
        }

        if (cliente.getDataNascimento() == null){
            throw new Exception(ERRO_DATA);
        }

        if (cliente.getEmail().isEmpty()){
            throw new Exception(ERRO_EMAIL);
        }

        if (cliente.getEndereco().isEmpty()){
            throw new Exception(ERRO_ENDERECO);
        }

        if (cliente.getNome().isEmpty()){
            throw new Exception(ERRO_NOME);
        }

        new DAOCliente(ctx).update(cliente);
    }

    @Override
    public void remove(Cliente cliente) throws Exception {

        if (cliente.getId() < 0){
            throw new Exception(ERRO_ID);
        }

        new DAOCliente(ctx).remove(cliente);

    }

    @Override
    public List<Cliente> getClientes() throws Exception {
        return new DAOCliente(ctx).getClientes();
    }
}
