package com.github.mavbraz.barbermobile.model;

import com.github.mavbraz.barbermobile.controller.INegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.services.APIUtils;

import retrofit2.Call;

public class DAOCliente implements INegocioCliente {

    @Override
    public boolean insert(Cliente cliente) {
        return true;
    }

    @Override
    public Call<Cliente> login(Cliente cliente) {
        return  APIUtils.getUsuarioService().loginUsuario(cliente);
    }

}
