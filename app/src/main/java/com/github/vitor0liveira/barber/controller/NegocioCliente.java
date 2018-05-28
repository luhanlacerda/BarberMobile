package com.github.vitor0liveira.barber.controller;

import com.github.vitor0liveira.barber.model.DAOCliente;
import com.github.vitor0liveira.barber.model.basicas.Cliente;

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

        new DAOCliente().insert(cliente);
    }

    @Override
    public void remove(Cliente cliente) throws Exception {

        if (cliente.getId() < 0){
            throw new Exception(ERRO_ID);
        }

        new DAOCliente().remove(cliente);

    }

    @Override
    public void refresh(Cliente cliente) throws Exception {

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

        new DAOCliente().refresh(cliente);

    }

    @Override
    public List<Cliente> getAll() throws Exception {
        return new DAOCliente().getAll();
    }
}
