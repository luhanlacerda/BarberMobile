package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListarServicosAdapter extends BaseAdapter {

    private final Map<Integer, Servico> servicosSelecionados;
    private final Context context;
    private List<Servico> servicos;
    private ListarServicosAdapterListener listener;

    public ListarServicosAdapter(Context context, List<Servico> servicos,
                                 Map<Integer, Servico> servicosSelecionados, ListarServicosAdapterListener listener) {
        this.context = context;

        if (servicos != null) {
            this.servicos = servicos;
        } else {
            this.servicos = new ArrayList<>();
        }

        if (servicosSelecionados != null) {
            this.servicosSelecionados = servicosSelecionados;
        } else {
            this.servicosSelecionados = new HashMap<>();
        }

        if (listener != null) {
            this.listener = listener;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListarServicosAdapterListener");
        }
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Step 1
        final Servico servico = servicos.get(position);
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
        holder.fieldValor.setText(context.getString(R.string.total, servico.getValor()));

        if (servicosSelecionados.containsKey(position)) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        return convertView;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        if (servicos != null) {
            this.servicos = servicos;
        }
    }

    public Map<Integer, Servico> getServicosSelecionados() {
        return servicosSelecionados;
    }

    public void onItemClick(int position) {
        Servico servico = servicos.get(position);

        if (!servicosSelecionados.containsKey(position)) {
            servicosSelecionados.put(position, servico);
            listener.itemMarcado(servico);
        } else {
            servicosSelecionados.remove(position);
            listener.itemDesmarcado(servico);
        }

        notifyDataSetChanged();
    }

    private static class ViewHolder{
        TextView fieldNome;
        TextView fieldDescricao;
        TextView fieldValor;
    }

    public interface ListarServicosAdapterListener {
        void itemMarcado(Servico servico);
        void itemDesmarcado(Servico servico);
    }

}
