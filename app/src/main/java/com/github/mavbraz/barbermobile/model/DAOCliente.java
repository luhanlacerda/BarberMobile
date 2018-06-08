package com.github.mavbraz.barbermobile.model;

import com.github.mavbraz.barbermobile.controller.INegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.services.APIUtils;

import retrofit2.Call;

public class DAOCliente implements INegocioCliente {

    @Override
    public Call<Cliente> insert(Cliente cliente) {
        return APIUtils.getUsuarioService().registrarUsuario(cliente);
    }

    @Override
    public Call<Cliente> login(Cliente cliente) {
        return APIUtils.getUsuarioService().loginUsuario(cliente);
    }

}
