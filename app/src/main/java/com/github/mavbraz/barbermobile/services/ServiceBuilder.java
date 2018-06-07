package com.github.mavbraz.barbermobile.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

  public static Retrofit retrofit = null;

  public static Retrofit getLogin(String url) {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(url)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }


}
