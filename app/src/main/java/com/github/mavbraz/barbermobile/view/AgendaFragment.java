package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mavbraz.barbermobile.R;

public class AgendaFragment extends Fragment {
    public static final String TAG = "agenda";

    private ViewPager mViewPager;
    private AgendaFragmentListener mListener;

    AbasPagerAdapter mAbasPagerAdapter;

    public AgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AgendaFragmentListener) {
            mListener = (AgendaFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        return inflater.inflate(R.layout.fragment_agenda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAbasPagerAdapter = new AbasPagerAdapter(getChildFragmentManager(), null);
        mViewPager = view.findViewById(R.id.pager);
        mViewPager.setAdapter(mAbasPagerAdapter);

        mListener.carregarAgendamentos(mAbasPagerAdapter);
    }

    public interface AgendaFragmentListener {
        void carregarAgendamentos(AbasPagerAdapter adapter);
    }
}
