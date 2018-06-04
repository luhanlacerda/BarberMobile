package com.github.mavbraz.barbermobile.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgendamentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendamentoFragment extends Fragment {
    private static final String AGENDAMENTO = "agendamento";

    private Agendamento mAgendamento;

    public AgendamentoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param agendamento {@link Agendamento}
     * @return A new instance of fragment AgendamentoFragment.
     */
    public static AgendamentoFragment newInstance(Agendamento agendamento) {
        AgendamentoFragment fragment = new AgendamentoFragment();
        Bundle args = new Bundle();
        args.putParcelable(AGENDAMENTO, agendamento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAgendamento = getArguments().getParcelable(AGENDAMENTO);
        }
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        if (mAgendamento == null || mAgendamento.getHorario() == 0 ||
                mAgendamento.getSituacao() == null || mAgendamento.getPagamento() == null ||
                mAgendamento.getServicos() == null) {
            view = inflater.inflate(R.layout.fragment_error, container, false);
            TextView txtError = view.findViewById(R.id.error);

            txtError.setText("Erro ao carregar o agendamento");
        } else {
            view = inflater.inflate(R.layout.fragment_agendamento, container, false);
            TextView txtHorario = view.findViewById(R.id.horario);
            TextView txtSituacao = view.findViewById(R.id.situacao);
            TextView txtPagamento = view.findViewById(R.id.pagamento);
            TextView txtServicos = view.findViewById(R.id.servicos);
            TextView txtCusto = view.findViewById(R.id.custo);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            txtHorario.setText(String.format("Horário: %s", simpleDateFormat.format(new Date(mAgendamento.getHorario() * 1000L))));
            txtSituacao.setText(String.format("Situação: %s", mAgendamento.getSituacao().name()));
            txtPagamento.setText(String.format("Pagamento: %s", mAgendamento.getPagamento().name()));
            txtServicos.setText(String.format("Serviços: %s", TextUtils.join(", ", mostrarServicos())));
            txtCusto.setText(String.format(Locale.getDefault(), "Custo: R$%.2f", mostrarCusto()));
        }

        return view;
    }

    private String[] mostrarServicos() {
        String[] servicos = new String[mAgendamento.getServicos().size()];

        for (int i=0; i < mAgendamento.getServicos().size(); i++) {
            servicos[i] = mAgendamento.getServicos().get(i).getNome();
        }

        return servicos;
    }

    private double mostrarCusto() {
        double custo = 0;

        for (Servico servico : mAgendamento.getServicos()) {
            custo += servico.getValor();
        }

        return custo;
    }

}
