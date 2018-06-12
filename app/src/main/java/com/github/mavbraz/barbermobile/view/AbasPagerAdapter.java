package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class AbasPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private List<Agendamento> mAgendamentos;

    public AbasPagerAdapter(Context context, FragmentManager fm, List<Agendamento> agendamentos) {
        super(fm);

        this.context = context;

        if (agendamentos != null) {
            this.mAgendamentos = agendamentos;
        } else {
            this.mAgendamentos = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return AgendamentoFragment.newInstance(mAgendamentos.get(position));
    }

    @Override
    public int getCount() {
        return mAgendamentos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.agendamento_n, position + 1);
    }

    public List<Agendamento> getAgendamentos() {
        return mAgendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.mAgendamentos = agendamentos;
    }
}
