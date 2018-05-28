package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.util.List;

public class ListarServicosAdapter extends BaseAdapter{
    private final List<Servico> servicos;
    private final Context context;

    public ListarServicosAdapter(Context context, List<Servico> servicos) {
        this.context = context;
        this.servicos = servicos;
    }

    @Override
    public int getCount() {
        return servicos.size();
    }

    @Override
    public Object getItem(int position) {
        return servicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return servicos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Servico servico  = servicos.get(position);

        return null;
    }
}
