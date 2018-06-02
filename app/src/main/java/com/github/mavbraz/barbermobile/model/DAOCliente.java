package com.github.mavbraz.barbermobile.model;

import com.github.mavbraz.barbermobile.controller.INegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;

public class DAOCliente implements INegocioCliente {


    @Override
    public boolean insert(Cliente cliente) {
        return false;
    }

    @Override
    public boolean login(Cliente cliente) {
        return cliente.getEmail().equals("admin@barbershop.com") && cliente.getSenha().equalsIgnoreCase("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
    }
}
