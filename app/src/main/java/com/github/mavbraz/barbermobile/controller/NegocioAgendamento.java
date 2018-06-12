package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOAgendamento;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import retrofit2.Call;

public class NegocioAgendamento implements INegocioAgendamento {

    private final Context context;

    public NegocioAgendamento(Context context) {
        this.context = context;
    }

    @Override
    public Call<Agendamento> solicitarAgendamento(Agendamento agendamento) {
        return new DAOAgendamento(context).solicitarAgendamento(agendamento);
    }

}
