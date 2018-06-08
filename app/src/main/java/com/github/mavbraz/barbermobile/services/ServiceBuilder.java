package com.github.mavbraz.barbermobile.services;

import android.content.Context;

import com.github.mavbraz.barbermobile.utils.SharedPreferencesManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://barbermobile.servehttp.com:3000";

    public static Retrofit getRetrofitInstance(final Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder request = chain.request().newBuilder();
                            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
                            if (sharedPreferencesManager.isLogged()) {
                                request.addHeader("Authorization",
                                        "Bearer " + sharedPreferencesManager.getToken()).build();
                            }

                            return chain.proceed(request.build());
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
