package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class AgendaTabletLandAdapter extends ArrayAdapter {

    private AgendaTabletLandAdapterListener listener;
    private List<Agendamento> agendamentos;
    private int selected;

    public AgendaTabletLandAdapter(Context context, List<Agendamento> agendamentos, AgendaTabletLandAdapterListener listener) {
        super(context, android.R.layout.simple_list_item_1);

        if (agendamentos != null) {
            this.agendamentos = agendamentos;
        } else {
            this.agendamentos = new ArrayList<>();
        }

        if (listener != null) {
            this.listener = listener;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AgendaTabletLandAdapterListener");
        }
    }

    public AgendaTabletLandAdapter(Context context, List<Agendamento> agendamentos, int selected, AgendaTabletLandAdapterListener listener) {
        this(context, agendamentos, listener);

        this.selected = selected;
    }

    @Override
    public int getCount() {
        return agendamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return getContext().getString(R.string.agendamento_n, position + 1);
    }

    @Override
    public long getItemId(int position) {
        return agendamentos.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);

        if (position == selected) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            listener.carregarFragment(agendamentos.get(selected));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        }

        return view;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public interface AgendaTabletLandAdapterListener {
        void carregarFragment(Agendamento agendamento);
    }

}
