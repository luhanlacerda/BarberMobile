package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;

import retrofit2.Call;

public interface INegocioCliente {

    Call<Cliente> insert(Cliente cliente) throws BarberException;
    Call<Cliente> login(Cliente cliente) throws BarberException;

}
