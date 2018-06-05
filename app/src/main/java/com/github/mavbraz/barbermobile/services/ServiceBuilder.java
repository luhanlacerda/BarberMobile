package com.github.mavbraz.barbermobile.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

  private static final String URL = "http://localhost:3000";

  // TODO: CREATE LOGGER
  private static Retrofit.Builder builder = new Retrofit.Builder()
      .baseUrl(URL).addConverterFactory(GsonConverterFactory.create());

  public static Retrofit retrofit = builder.build();

  public static <S> S buildService(Class<S>  serviceType){
    return retrofit.create(serviceType);
  }

}
