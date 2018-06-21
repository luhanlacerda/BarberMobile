package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.DAOCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;
import com.github.mavbraz.barbermobile.utils.BarberUtil;

import retrofit2.Call;

public class NegocioCliente implements INegocioCliente {

    private final Context context;

    public NegocioCliente(Context context) {
        this.context = context;
    }

    @Override
    public Call<Cliente> insert(Cliente cliente) throws BarberException {
        BarberException exception = new BarberException(true);

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.nome_requerido), BarberException.NOME));
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.cpf_requerido), BarberException.CPF));
        } else if (!cliente.getCpf().matches("^[0-9]{11}$")) {
            exception.addException(new BarberException(context.getResources().getString(R.string.cpf_invalido), BarberException.CPF));
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.email_requerido), BarberException.EMAIL));
        } else if (!BarberUtil.isEmailValid(cliente.getEmail())) {
            exception.addException(new BarberException(context.getResources().getString(R.string.email_invalido), BarberException.EMAIL));
        }

        if (cliente.getSenha() == null || cliente.getSenha().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.senha_requerida), BarberException.SENHA));
        }

        if (!exception.getExceptions().isEmpty()) {
            throw exception;
        }

        return new DAOCliente(context).insert(cliente);
    }

    public Call<Cliente> login(Cliente cliente) throws BarberException {
        BarberException exception = new BarberException(true);

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.email_requerido), BarberException.EMAIL));
        } else if (!BarberUtil.isEmailValid(cliente.getEmail())) {
            exception.addException(new BarberException(context.getResources().getString(R.string.email_invalido), BarberException.EMAIL));
        }

        if (cliente.getSenha() == null || cliente.getSenha().trim().isEmpty()) {
            exception.addException(new BarberException(context.getResources().getString(R.string.senha_requerida), BarberException.SENHA));
        }

        if (!exception.getExceptions().isEmpty()) {
            throw exception;
        }

        return new DAOCliente(context).login(cliente);
    }

}
