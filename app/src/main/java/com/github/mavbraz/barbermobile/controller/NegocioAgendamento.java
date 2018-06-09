package com.github.mavbraz.barbermobile.controller;

import android.content.Context;

import com.github.mavbraz.barbermobile.model.DAOAgendamento;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Resposta;

import retrofit2.Call;

public class NegocioAgendamento implements INegocioAgendamento {

    private Context context;

    public NegocioAgendamento(Context context) {
        this.context = context;
    }

    @Override
    public Call<Resposta> solicitarAgendamento(Agendamento agendamento) {
        return new DAOAgendamento(context).solicitarAgendamento(agendamento);
    }

}
