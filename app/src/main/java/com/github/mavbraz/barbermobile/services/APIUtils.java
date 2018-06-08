package com.github.mavbraz.barbermobile.services;

import android.content.Context;

public class APIUtils {

    public static UsuarioService getUsuarioService(Context context) {
        return ServiceBuilder.getRetrofitInstance(context).create(UsuarioService.class);
    }

}
