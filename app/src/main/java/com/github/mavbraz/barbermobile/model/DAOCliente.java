package com.github.mavbraz.barbermobile.model;


import com.github.mavbraz.barbermobile.controller.INegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DAOCliente implements INegocioCliente {
  public static Retrofit retrofit = null;

    @Override
    public boolean insert(Cliente cliente) {
        return true;
    }

    @Override
    public boolean login(Cliente cliente) {

         return cliente.getEmail().equals("admin@barbershop.com")
           && cliente.getSenha().equalsIgnoreCase("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
    }

  public void doLogin(String email, String password) {
    UsuarioService usuarioService = retrofit.create(UsuarioService.class);
    Call<Cliente> call = usuarioService.loginUsuario(email, password);

    call.enqueue(new Callback<Cliente>() {
      @Override
      public void onResponse(Call<Cliente> call, Response<Cliente> response) {
        if(response.isSuccessful()){
          Cliente resCliente = response.body();
          if(resCliente.equals("true"));
        }
      }

      @Override
      public void onFailure(Call<Cliente> call, Throwable t) {

      }
    });

  }
}
