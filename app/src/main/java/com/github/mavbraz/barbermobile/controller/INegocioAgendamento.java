package com.github.mavbraz.barbermobile.controller;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Resposta;

import retrofit2.Call;

public interface INegocioAgendamento {

    Call<Resposta> solicitarAgendamento(Agendamento agendamento);

}
