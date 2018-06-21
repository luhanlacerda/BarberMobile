package com.github.mavbraz.barbermobile.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioAgendamento;
import com.github.mavbraz.barbermobile.controller.NegocioServico;
import com.github.mavbraz.barbermobile.model.BarberSQLHelper;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Pagamento;
import com.github.mavbraz.barbermobile.model.basicas.Situacao;
import com.github.mavbraz.barbermobile.model.basicas.TodosServicos;
import com.github.mavbraz.barbermobile.utils.AgendamentoTask;
import com.github.mavbraz.barbermobile.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements DuoMenuView.OnMenuClickListener,
        AgendaFragment.AgendaFragmentListener,
        SolicitarAgendamentoFragment.SolicitarServicoFragmentListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    private int selectedMenu;
    private ArrayList<String> mTitles = new ArrayList<>();
    private SharedPreferencesManager mSharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        if (savedInstanceState == null) {
            // Show main fragment in container
            goToFragment(new MainFragment(), MainFragment.TAG, false);
        } else {
            selectedMenu = savedInstanceState.getInt("selectedMenu");
        }

        setTitle(mTitles.get(selectedMenu));
        mMenuAdapter.setViewSelected(selectedMenu, true);

        mSharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());
        String email = mSharedPreferencesManager.getEmail();
        if (email != null) {
            TextView txtHeader = mViewHolder.mDuoMenuView.getHeaderView().findViewById(R.id.header_text);
            txtHeader.setText(email);
        } else {
            this.onFooterClicked();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedMenu", selectedMenu);
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        mSharedPreferencesManager.removeToken();
        mSharedPreferencesManager.removeEmail();

        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onHeaderClicked() {
    }

    private boolean goToFragment(Fragment fragment, String tag, boolean addToBackStack) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null &&
                getSupportFragmentManager().findFragmentByTag(tag).isVisible()) {
            return false;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.replace(R.id.container, fragment, tag);

        transaction.commit();

        return true;
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        boolean wasAdded = false;

        // Navigate to the right fragment
        switch (position) {
            case 0:
                wasAdded = goToFragment(new MainFragment(), MainFragment.TAG, true);
                break;
            case 1:
                wasAdded = goToFragment(new AgendaFragment(), AgendaFragment.TAG, true);
                break;
            case 2:
                wasAdded = goToFragment(new SolicitarAgendamentoFragment(), SolicitarAgendamentoFragment.TAG, true);
                break;
        }

        if (wasAdded) {
            // Set selected menu
            selectedMenu = position;

            // Set the toolbar title
            setTitle(mTitles.get(position));

            // Set the right options selected
            mMenuAdapter.setViewSelected(position, true);

            // Close the drawer
            mViewHolder.mDuoDrawerLayout.closeDrawer();
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0) {
            moveTaskToBack(true);
        } else {
            int position;
            if (backStackCount > 1) {
                switch (getSupportFragmentManager().getBackStackEntryAt(backStackCount - 2).getName()) {
                    case AgendaFragment.TAG:
                        position = 1;
                        break;
                    case SolicitarAgendamentoFragment.TAG:
                        position = 2;
                        break;
                    default:
                        position = 0;
                }
            } else {
                position = 0;
            }

            selectedMenu = position;

            setTitle(mTitles.get(position));
            mMenuAdapter.setViewSelected(position, true);
            super.onBackPressed();
        }
    }

    @Override
    public void carregarAgendamentos(Object adapter) {
        AgendamentoTask task = new AgendamentoTask(new WeakReference<>((Context) this), adapter);
        task.execute();
    }

    @Override
    public void carregarAgendamentoFragment(Agendamento agendamento) {
        Fragment fragment = AgendamentoFragment.newInstance(agendamento);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_agendamento, fragment);
        transaction.commit();
    }

    @Override
    public void mostrarMensagem(String message) {
        Snackbar.make(mViewHolder.mDuoDrawerLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void solicitarAgendamento(final Agendamento agendamento) {
        new NegocioAgendamento(getApplicationContext()).solicitarAgendamento(agendamento).enqueue(
                new Callback<Agendamento>() {
                    @Override
                    public void onResponse(@NonNull Call<Agendamento> call, @NonNull Response<Agendamento> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            agendamento.setId(response.body().getId());
                            agendamento.setPagamento(Pagamento.PENDENTE);
                            agendamento.setSituacao(Situacao.MARCADO);
                            BarberSQLHelper sqlHelper = new BarberSQLHelper(getApplicationContext());
                            sqlHelper.sincronizarAgendamentos(Collections.singletonList(agendamento));
                            onBackPressed();
                            mostrarMensagem(getString(R.string.agendamento_sucesso));
                        } else {
                            mostrarMensagem(getString(R.string.erro_agendamento));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Agendamento> call, @NonNull Throwable t) {
                        mostrarMensagem(getString(R.string.erro_conexao));
                    }
                }
        );
    }

    @Override
    public void carregarServicos(final ListarServicosAdapter adapter, final ProgressDialog progressDialog) {
        new NegocioServico(getApplicationContext()).getAll().enqueue(
                new Callback<TodosServicos>() {
                    @Override
                    public void onResponse(@NonNull Call<TodosServicos> call, @NonNull Response<TodosServicos> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getServicos() != null) {
                            adapter.setServicos(response.body().getServicos());
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                mostrarMensagem(jsonObject.getString("message"));
                            } catch (NullPointerException | IOException | JSONException ex) {
                                mostrarMensagem(getString(R.string.erro_servicos));
                            }

                            progressDialog.dismiss();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TodosServicos> call, @NonNull Throwable t) {
                        mostrarMensagem(getString(R.string.erro_conexao));
                        progressDialog.dismiss();
                        onBackPressed();
                    }
                }
        );
    }

    private class ViewHolder {
        private final DuoDrawerLayout mDuoDrawerLayout;
        private final DuoMenuView mDuoMenuView;
        private final Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
        }
    }
}