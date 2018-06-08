package com.github.mavbraz.barbermobile.view;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;
import com.github.mavbraz.barbermobile.utils.SharedPreferencesManager;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    EditText edtEmail;
    EditText edtSenha;
    CoordinatorLayout coordinatorLayout;

    private SharedPreferencesManager mSharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        if (mSharedPreferencesManager.isLogged()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.email);
        edtSenha = findViewById(R.id.password);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);

        edtSenha.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            logarCliente();
        } else if (view.getId() == R.id.btn_signup) {
            startActivity(new Intent(this, RegistrarActivity.class));
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == edtSenha && EditorInfo.IME_ACTION_DONE == actionId) {
            logarCliente();

            return false;
        }

        return true;
    }

    private void logarCliente() {
        try {
            final Cliente cliente = new Cliente();
            cliente.setEmail(edtEmail.getText().toString());
            cliente.setSenha(edtSenha.getText().toString());

            new NegocioCliente().login(cliente).enqueue(
                    new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                                mSharedPreferencesManager.saveToken(response.body().getToken());
                                mSharedPreferencesManager.saveEmail(cliente.getEmail());

                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            } else {
                                Snackbar.make(coordinatorLayout, "Falha ao logar. Tente novamente",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Snackbar.make(coordinatorLayout, "Falha ao logar. Tente novamente",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
            );
        } catch (BarberException loginException) {
            try {
                for (BarberException barberException : loginException.getExceptions()) {
                    if (barberException.getComponent().equals(BarberException.EMAIL)) {
                        edtEmail.setError(barberException.getMessage());
                    } else if (barberException.getComponent().equals(BarberException.SENHA)) {
                        edtSenha.setError(barberException.getMessage());
                    }
                }
            } catch (BarberException listException) {
                Snackbar.make(coordinatorLayout, listException.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        }

    }
}
