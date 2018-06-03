package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;

public interface INegocioCliente {

    boolean insert(Cliente cliente) throws BarberException;
    boolean login(Cliente cliente) throws BarberException;

}
