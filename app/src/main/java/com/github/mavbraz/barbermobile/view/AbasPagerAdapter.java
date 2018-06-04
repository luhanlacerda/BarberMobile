package com.github.mavbraz.barbermobile.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

import java.util.List;
import java.util.Locale;

public class AbasPagerAdapter extends FragmentPagerAdapter {

    private List<Agendamento> mAgendamentos;

    public AbasPagerAdapter(FragmentManager fm, List<Agendamento> agendamentos) {
        super(fm);

        this.mAgendamentos = agendamentos;
    }

    @Override
    public Fragment getItem(int position) {
        if (mAgendamentos == null || mAgendamentos.get(position) == null) {
            return null;
        }

        return AgendamentoFragment.newInstance(mAgendamentos.get(position));
    }

    @Override
    public int getCount() {
        if (mAgendamentos == null) {
            return 0;
        }

        return mAgendamentos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mAgendamentos == null || mAgendamentos.get(position) == null) {
            return "NOT FOUND";
        }

        return String.format(Locale.getDefault(), "AGENDAMENTO %d", position + 1);
    }
}
