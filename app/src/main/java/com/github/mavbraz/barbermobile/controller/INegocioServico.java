package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;

import retrofit2.Call;

public interface INegocioServico {

    Call<TodosServicos> getAll();

}
