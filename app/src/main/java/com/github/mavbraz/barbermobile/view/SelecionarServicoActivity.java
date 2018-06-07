package com.github.mavbraz.barbermobile.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelecionarServicoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String RESULT_SERVICOS = "servicos";

    ListarServicosAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_servico);

        List<Servico> servicos = Arrays.asList(
                new Servico(1, "Corte de cabelo", "Fique como o Chewbacca", 30.5),
                new Servico(2, "Barba e bigode", "Fique como a barbie", 150.8));

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

}

