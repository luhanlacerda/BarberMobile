package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
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

        // Step 1
        Servico servico = servicos.get(position);
        // Step 2
        ViewHolder holder;
        if (convertView == null){ /* View nova, temos que criá-la*/
            convertView = LayoutInflater.from(context).inflate(R.layout.item_servico, null);
            holder = new ViewHolder();
            holder.fieldNome = convertView.findViewById(R.id.txtNome);
            holder.fieldDescricao = convertView.findViewById(R.id.txtDescricao);
            holder.fieldValor = convertView.findViewById(R.id.txtValor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fieldNome.setText(servico.getNome());
        holder.fieldDescricao.setText(servico.getDescricao());
        holder.fieldValor.setText(String.format("R$ %.2f", servico.getValor()));

        return convertView;
    }

    private static class ViewHolder{
        TextView fieldNome;
        TextView fieldDescricao;
        TextView fieldValor;
    }
}
