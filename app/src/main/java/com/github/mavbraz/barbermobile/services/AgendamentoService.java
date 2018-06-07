package com.github.mavbraz.barbermobile.services;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AgendamentoService {


  @POST("agendamento")
  Call<Agendamento> solicitarAgendamento(@Body Agendamento agendamento);

}
