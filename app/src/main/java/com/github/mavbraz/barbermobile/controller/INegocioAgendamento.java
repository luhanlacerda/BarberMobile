package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import retrofit2.Call;

public interface INegocioAgendamento {

    Call<Agendamento> solicitarAgendamento(Agendamento agendamento);

}
