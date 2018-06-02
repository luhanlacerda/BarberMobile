package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;
import com.github.mavbraz.barbermobile.utils.BarberUtil;

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
    public boolean insert(Cliente cliente) throws BarberException {
        return false;
    }

    public boolean login(Cliente cliente) throws BarberException {
        BarberException exception = new BarberException(true);

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            exception.addException(new BarberException("E-mail requerido", BarberException.EMAIL));
        } else if (!BarberUtil.isEmailValid(cliente.getEmail())) {
            exception.addException(new BarberException("E-mail inválido", BarberException.EMAIL));
        }

        if (cliente.getSenha() == null || cliente.getSenha().trim().isEmpty()) {
            exception.addException(new BarberException("Senha requerida", BarberException.SENHA));
        }

        if (!exception.getExceptions().isEmpty()) {
            throw exception;
        }

        return new DAOCliente().login(cliente);
    }

}
