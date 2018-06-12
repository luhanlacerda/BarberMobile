package com.github.mavbraz.barbermobile.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.github.mavbraz.barbermobile.model.BarberSQLHelper;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.services.ServiceBuilder;
import com.github.mavbraz.barbermobile.view.AbasPagerAdapter;
import com.github.mavbraz.barbermobile.view.AgendaTabletLandAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class AgendamentoTask extends AsyncTask<Void, Void, List<Agendamento>>{

    private WeakReference<Context> context;
    private Object adapter;
    private ProgressDialog progressDialog;

    public AgendamentoTask(WeakReference<Context> context, Object adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context.get(),"Agendamentos","Carregando...");
    }

    @SuppressWarnings("EmptyCatchBlock")
    @Override
    protected List<Agendamento> doInBackground(Void... voids) {
        List<Agendamento> agendamentos = null;

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context.get());
        if (!sharedPreferencesManager.isLogged()) {
            return null;
        }

        try {
            URL url = new URL(ServiceBuilder.BASE_URL + "/agendamento/cliente");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(20000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + sharedPreferencesManager.getToken());

            conn.connect();
            InputStream inputStream = conn.getInputStream();
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

            JSONObject jsonObject = new JSONObject(scanner.hasNext() ? scanner.next() : "");
            JSONArray jsonArray = jsonObject.getJSONArray("agendamentos");

            Gson gson = new Gson();
            agendamentos = gson.fromJson(jsonArray.toString(), new TypeToken<List<Agendamento>>(){}.getType());

            conn.disconnect();
        } catch (IOException | JSONException e) {
        }

        return agendamentos;
    }

    @Override
    protected void onPostExecute(List<Agendamento> agendamentos) {
        super.onPostExecute(agendamentos);

        progressDialog.dismiss();

        BarberSQLHelper sqlHelper = new BarberSQLHelper(context.get());
        if (agendamentos != null) {
            sqlHelper.sincronizarAgendamentos(agendamentos);
        } else {
            agendamentos = sqlHelper.carregarAgendamentos();
        }

        if (adapter instanceof AbasPagerAdapter) {
            AbasPagerAdapter abasPagerAdapter = (AbasPagerAdapter) adapter;
            abasPagerAdapter.setAgendamentos(agendamentos);
            abasPagerAdapter.notifyDataSetChanged();
        } else {
            AgendaTabletLandAdapter agendaTabletAdapter = (AgendaTabletLandAdapter) adapter;
            agendaTabletAdapter.setAgendamentos(agendamentos);
            agendaTabletAdapter.notifyDataSetChanged();
        }

    }

}