package com.github.mavbraz.barbermobile.services;

public class APIUtils {

    public static UsuarioService getUsuarioService() {
        return ServiceBuilder.getRetrofitInstance().create(UsuarioService.class);
    }

}
