package com.github.mavbraz.barbermobile.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AbasPagerAdapter extends FragmentPagerAdapter {

    private List<Agendamento> mAgendamentos;

    public AbasPagerAdapter(FragmentManager fm, List<Agendamento> agendamentos) {
        super(fm);

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
        return String.format(Locale.getDefault(), "AGENDAMENTO %d", mAgendamentos.get(position).getId());
    }

    public List<Agendamento> getAgendamentos() {
        return mAgendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.mAgendamentos = agendamentos;
    }
}
