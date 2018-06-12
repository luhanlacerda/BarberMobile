package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;

public class AgendaFragment extends Fragment implements AgendaTabletLandAdapter.AgendaTabletLandAdapterListener {

    public static final String TAG = "agenda";

    private ViewPager viewPager;
    private ListView listView;
    private Context context;
    private AgendaFragmentListener mListener;
    private AgendaTabletLandAdapter agendaTabletAdapter;
    private AbasPagerAdapter abasPagerAdapter;

    public AgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof AgendaFragmentListener) {
            mListener = (AgendaFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AgendaFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        if (isTablet() || isLandscape()) {
            listView = view.findViewById(R.id.list_agendamentos);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    agendaTabletAdapter.setSelected(position);
                    agendaTabletAdapter.notifyDataSetChanged();
                }
            });
        } else {
            viewPager = view.findViewById(R.id.pager);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            if (isTablet() || isLandscape()) {
                agendaTabletAdapter = new AgendaTabletLandAdapter(context, null,  this);
                listView.setAdapter(agendaTabletAdapter);
                mListener.carregarAgendamentos(agendaTabletAdapter);
            } else {
                abasPagerAdapter = new AbasPagerAdapter(context, getChildFragmentManager(), null);
                viewPager.setAdapter(abasPagerAdapter);
                mListener.carregarAgendamentos(abasPagerAdapter);
            }
        } else {
            if (isSmartphone()) {
                if (isPortrait()) {
                    abasPagerAdapter = new AbasPagerAdapter(context, getChildFragmentManager(), agendaTabletAdapter.getAgendamentos());
                    viewPager.setAdapter(abasPagerAdapter);
                    viewPager.setCurrentItem(agendaTabletAdapter.getSelected());
                    agendaTabletAdapter = null;
                } else {
                    agendaTabletAdapter = new AgendaTabletLandAdapter(context, abasPagerAdapter.getAgendamentos(),
                            viewPager.getCurrentItem(), this);
                    listView.setAdapter(agendaTabletAdapter);
                    abasPagerAdapter = null;
                }
            } else {
                listView.setAdapter(agendaTabletAdapter);
            }
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }

    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }

    @Override
    public void carregarFragment(Agendamento agendamento) {
        mListener.carregarAgendamentoFragment(agendamento);
    }

    public interface AgendaFragmentListener {
        void carregarAgendamentos(Object adapter);
        void carregarAgendamentoFragment(Agendamento agendamento);
    }

}
