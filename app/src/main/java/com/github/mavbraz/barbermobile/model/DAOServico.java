package com.github.mavbraz.barbermobile.model;

import android.content.Context;

import com.github.mavbraz.barbermobile.controller.INegocioServico;
import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;
import com.github.mavbraz.barbermobile.services.APIUtils;

import retrofit2.Call;

public class DAOServico implements INegocioServico {

    private Context context;

    public DAOServico(Context context) {
        this.context = context;
    }

    @Override
    public Call<TodosServicos> getAll() {
        return APIUtils.getServicoService(context).carregarServicos();
    }

}
