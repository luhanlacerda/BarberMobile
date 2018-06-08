package com.github.mavbraz.barbermobile.model;

import android.content.Context;

import com.github.mavbraz.barbermobile.controller.INegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.services.APIUtils;

import retrofit2.Call;

public class DAOCliente implements INegocioCliente {

    private Context context;

    public DAOCliente(Context context) {
        this.context = context;
    }

    @Override
    public Call<Cliente> insert(Cliente cliente) {
        return APIUtils.getUsuarioService(context).registrarUsuario(cliente);
    }

    @Override
    public Call<Cliente> login(Cliente cliente) {
        return APIUtils.getUsuarioService(context).loginUsuario(cliente);
    }

}
