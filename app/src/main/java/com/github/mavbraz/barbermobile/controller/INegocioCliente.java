package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;

import java.util.List;

public interface INegocioCliente {

    public void insert(Cliente cliente) throws Exception;

    public void update(Cliente cliente) throws Exception;

    public void remove(Cliente cliente) throws Exception;

    public List<Cliente> getClientes() throws Exception;
}
