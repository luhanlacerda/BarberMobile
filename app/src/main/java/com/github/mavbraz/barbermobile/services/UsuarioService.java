package com.github.mavbraz.barbermobile.services;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioService {

  @POST("usuario/registrar/cliente")
  Call<Cliente> registrarUsuario(@Body Cliente cliente);

  @GET("usuario/login/cliente")
  Call<Cliente> loginUsuario(@Body Cliente cliente);

}
