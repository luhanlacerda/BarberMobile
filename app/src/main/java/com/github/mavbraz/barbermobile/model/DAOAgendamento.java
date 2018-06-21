package com.github.mavbraz.barbermobile.model;

import android.content.Context;

import com.github.mavbraz.barbermobile.controller.INegocioAgendamento;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.services.APIUtils;

import retrofit2.Call;

public class DAOAgendamento implements INegocioAgendamento {

    private final Context context;

    public DAOAgendamento(Context context) {
        this.context = context;
    }

    @Override
    public Call<Agendamento> solicitarAgendamento(Agendamento agendamento) {
        return APIUtils.getAgendamentoService(context).solicitarAgendamento(agendamento);
    }

}
