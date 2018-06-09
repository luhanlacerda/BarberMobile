package com.github.mavbraz.barbermobile.services;

import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicoService {

    @GET("servico")
    Call<TodosServicos> carregarServicos();

}
