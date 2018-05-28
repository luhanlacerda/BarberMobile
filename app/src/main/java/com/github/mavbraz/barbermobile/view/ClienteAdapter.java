package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;

import java.util.List;

public class ClienteAdapter extends BaseAdapter{
    private final List<Cliente> clientes;
    private final Context context;

    public ClienteAdapter(Context context, List<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clientes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente cliente  = clientes.get(position);

        return null;
    }
}
