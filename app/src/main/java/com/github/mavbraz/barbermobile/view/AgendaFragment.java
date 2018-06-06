package com.github.mavbraz.barbermobile.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Pagamento;
import com.github.mavbraz.barbermobile.model.basicas.Servico;
import com.github.mavbraz.barbermobile.model.basicas.Situacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendaFragment extends Fragment {
    public static final String TAG = "agenda";

    private static final String AGENDAMENTOS = "agendamentos";

    private List<Agendamento> mAgendamentos;
    private AbasPagerAdapter mAbasPagerAdapter;
    private ViewPager mViewPager;

    public AgendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AgendaFragment.
     */
    public static AgendaFragment newInstance() {
        Agendamento agendamento1 = new Agendamento();
        agendamento1.setId(1);
        agendamento1.setHorario(1528062164L);
        agendamento1.setSituacao(Situacao.MARCADO);
        agendamento1.setPagamento(Pagamento.PENDENTE);
        agendamento1.setServicos(Arrays.asList(
                        new Servico(1, "Corte de Cabelo", null, 30.5),
                        new Servico(2, "Hidratação", null, 25.5)));

        Agendamento agendamento2 = new Agendamento();
        agendamento1.setId(2);
        agendamento2.setHorario(1528762164L);
        agendamento2.setSituacao(Situacao.REALIZADO);
        agendamento2.setPagamento(Pagamento.REALIZADO);
        agendamento2.setServicos(Arrays.asList(new Servico(1, "Corte de Cabelo", null, 30.5)));

        List<Agendamento> agendamentos = new ArrayList<>();
        agendamentos.add(agendamento1);
        agendamentos.add(agendamento2);

        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AGENDAMENTOS, (ArrayList<? extends Parcelable>) agendamentos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAgendamentos = getArguments().getParcelableArrayList(AGENDAMENTOS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if (mAgendamentos == null) {
            view = inflater.inflate(R.layout.fragment_error, container, false);
            TextView txtError = view.findViewById(R.id.error);

            txtError.setText("Erro ao carregar a agenda");
        } else {
            view = inflater.inflate(R.layout.fragment_agenda, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAgendamentos != null) {
            mAbasPagerAdapter = new AbasPagerAdapter(getChildFragmentManager(), mAgendamentos);
            mViewPager = view.findViewById(R.id.pager);
            mViewPager.setAdapter(mAbasPagerAdapter);
        }
    }
}
