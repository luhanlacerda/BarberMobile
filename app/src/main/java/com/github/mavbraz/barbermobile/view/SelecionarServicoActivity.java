package com.github.mavbraz.barbermobile.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioServico;
import com.github.mavbraz.barbermobile.model.basicas.Servico;
import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelecionarServicoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String RESULT_SERVICOS = "servicos";

    ListarServicosAdapter adapter;
    ListView listView;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_servico);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Serviços", "Carregando...");
        progressDialog.show();

        new NegocioServico(getApplicationContext()).getAll().enqueue(
                new Callback<TodosServicos>() {
                    @Override
                    public void onResponse(@NonNull Call<TodosServicos> call, @NonNull Response<TodosServicos> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getServicos() != null) {
                            setLayout(response.body().getServicos());
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                setError(jsonObject.getString("message"));
                            } catch (NullPointerException | IOException | JSONException ex) {
                                setError("Falha ao obter serviços. Tente novamente mais tarde");
                            }
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<TodosServicos> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            setError("Erro ao tentar conectar com o servidor");
                        } else {
                            setError("Falha ao obter serviços. Tente novamente mais tarde");
                        }

                        progressDialog.dismiss();
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirmar_servico) {
            List<Servico> checked = new ArrayList<>();

            for (int i = 0; i < listView.getCheckedItemPositions().size(); i++) {
                if (listView.getCheckedItemPositions().valueAt(i)) {
                    int position = listView.getCheckedItemPositions().keyAt(i);

                    checked.add(adapter.getServicos().get(position));
                }
            }

            Intent data = new Intent();
            data.putParcelableArrayListExtra(RESULT_SERVICOS, (ArrayList<? extends Parcelable>) checked);

            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    public void setLayout(List<Servico> servicos) {
        adapter = new ListarServicosAdapter(this, servicos);

        listView = findViewById(R.id.list_servicos);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (listView.isItemChecked(position)) {
                    view.setBackgroundColor(Color.GRAY);
                    listView.setItemChecked(position, true);
                } else {
                    view.setBackgroundColor(Color.WHITE);
                    listView.setItemChecked(position, false);
                }
            }
        });

        findViewById(R.id.confirmar_servico).setOnClickListener(this);
    }

    private void setError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE).show();
    }

}

