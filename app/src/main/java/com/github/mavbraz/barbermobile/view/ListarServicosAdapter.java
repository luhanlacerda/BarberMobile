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
import java.util.List;
import java.util.Locale;

public class ListarServicosAdapter extends BaseAdapter {

    private List<Servico> servicos;
    private List<Integer> servicosSelecionados;
    private Context context;
    private ListarServicosAdapterListener listener;

    public ListarServicosAdapter(Context context, List<Servico> servicos,
                                 List<Integer> servicosSelecionados, ListarServicosAdapterListener listener) {
        this.context = context;
        if (servicos != null) {
            this.servicos = servicos;
        } else {
            this.servicos = new ArrayList<>();
        }
        if (servicosSelecionados != null) {
            this.servicosSelecionados = servicosSelecionados;
        } else {
            this.servicosSelecionados = new ArrayList<>();
        }
        this.listener = listener;
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
        holder.fieldValor.setText(String.format(Locale.getDefault(), "R$%.2f", servico.getValor()));

        if (servicosSelecionados.contains(position)) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        convertView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!servicosSelecionados.contains(position)) {
                            servicosSelecionados.add(position);
                            v.setBackgroundColor(Color.GRAY);
                            listener.itemMarcado(servico);
                        } else {
                            servicosSelecionados.remove(Integer.valueOf(position));
                            v.setBackgroundColor(Color.WHITE);
                            listener.itemDesmarcado(servico);
                        }
                    }
                }
        );

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

    public List<Integer> getServicosSelecionados() {
        return servicosSelecionados;
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
