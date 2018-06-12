package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOServico;
import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;

import retrofit2.Call;

public class NegocioServico implements INegocioServico {

    private final Context context;

    public NegocioServico(Context context) {
        this.context = context;
    }

    @Override
    public Call<TodosServicos> getAll() {
        return new DAOServico(context).getAll();
    }

}
