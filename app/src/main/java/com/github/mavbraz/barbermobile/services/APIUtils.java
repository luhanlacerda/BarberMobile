package com.github.mavbraz.barbermobile.services;

public class APIUtils {

  public static final String BASE_URL = "http://localhost:3000";

  public static UsuarioService getUsuarioService() {
    return ServiceBuilder.getLogin(BASE_URL).create(UsuarioService.class);
  }

}
