package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;
import com.github.mavbraz.barbermobile.utils.BarberUtil;

import retrofit2.Call;

public class NegocioCliente implements INegocioCliente {

    private Context context;

    public NegocioCliente(Context context) {
        this.context = context;
    }

    @Override
    public Call<Cliente> insert(Cliente cliente) throws BarberException {
        BarberException exception = new BarberException(true);

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            exception.addException(new BarberException("Nome requerido", BarberException.NOME));
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            exception.addException(new BarberException("CPF requerido", BarberException.CPF));
        } else if (!cliente.getCpf().matches("^[0-9]{11}$")) {
            exception.addException(new BarberException("CPF inválido", BarberException.CPF));
        }

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

        return new DAOCliente(context).insert(cliente);
    }

    public Call<Cliente> login(Cliente cliente) throws BarberException {
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

        return new DAOCliente(context).login(cliente);
    }

}
