package com.github.mavbraz.barbermobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;
import com.github.mavbraz.barbermobile.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnRegistrar;
    private Button btnLogin;
    private CoordinatorLayout coordinatorLayout;

    private SharedPreferencesManager mSharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mSharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        edtCpf = findViewById(R.id.edt_cpf);
        edtNome = findViewById(R.id.edt_nome);
        edtEmail = findViewById(R.id.edt_email);
        edtSenha = findViewById(R.id.edt_password);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        btnRegistrar = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        edtSenha.setOnEditorActionListener(this);

        edtCpf.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                //quando o texto é alterado o onTextChanged é chamado, essa flag evita a chamada infinita desse metodo

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                //ao apagar o texto, a máscara é removida, entao o posicionamento do cursor precisa saber
                // se o texto atual tinha ou nao, mascara
                boolean hasMask = charSequence.toString().indexOf('.') > -1 || charSequence.toString().indexOf('-') > -1;

                //remove o '.' e o '-'
                String str = charSequence.toString().replaceAll("[.]", "").replaceAll("[-]", "");

                //os parametros before e after dizem o tamanho anterior e atual da String,
                //se after > before é porque está digitando, caso contrário, está apagando
                if (after > before) {
                    //se tem mais de 8 caracteres sem mascara, coloca o '.' e o '-'
                    if (str.length() > 8) {
                        str = str.substring(0, 3) + '.' + str.substring(3, 6) + '.' +
                                str.substring(6, 9) + '-' + str.substring(9);
                        // se tem mais de 3, só coloca o ponto
                    } else if (str.length() > 3) {
                        str = str.substring(0, 3) + '.' + str.substring(3);
                    }
                    // seta a flag pra evitar chamada infinita
                    isUpdating = true;
                    //seta o novo text
                    edtCpf.setText(str);
                    //seta a posicao do cursor
                    edtCpf.setSelection(edtCpf.getText().length());
                } else {
                    isUpdating = true;
                    edtCpf.setText(str);
                    // se estiver apagando posiciona o cursor no local correto. isso trata a delecao dos caracteres da mascara
                    edtCpf.setSelection(Math.max(0, Math.min(hasMask ? start - before : start, str.length())));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_signup) {
            registrarCliente();
        } else if (view.getId() == R.id.btn_login) {
            startActivity(new Intent(this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == edtSenha && EditorInfo.IME_ACTION_DONE == actionId) {
            registrarCliente();

            return false;
        }

        return true;
    }

    private void registrarCliente() {
        if (mSharedPreferencesManager.isLogged()) {
            return;
        }

        try {
            setButtons(false);
            final Cliente cliente = new Cliente();
            cliente.setNome(edtNome.getText().toString());
            cliente.setCpf(edtCpf.getText().toString());
            cliente.setEmail(edtEmail.getText().toString());
            cliente.setSenha(edtSenha.getText().toString());

            new NegocioCliente(getApplicationContext()).insert(cliente).enqueue(
                    new Callback<Cliente>() {
                        @Override
                        public void onResponse(@NonNull  Call<Cliente> call, @NonNull  Response<Cliente> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                                mSharedPreferencesManager.saveToken(response.body().getToken());
                                mSharedPreferencesManager.saveEmail(cliente.getEmail());

                                startActivity(new Intent(RegistrarActivity.this, MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            } else {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    setError(jsonObject.getString("message"));
                                } catch (NullPointerException|IOException|JSONException ex) {
                                    setError(getString(R.string.erro_registrar));
                                } finally {
                                    setButtons(true);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {
                            setError(getString(R.string.erro_conexao));
                            setButtons(true);
                        }
                    }
            );
        } catch (BarberException loginException) {
            try {
                for (BarberException barberException : loginException.getExceptions()) {
                    if (barberException.getComponent().equals(BarberException.NOME)) {
                        edtNome.setError(barberException.getMessage());
                    } else if (barberException.getComponent().equals(BarberException.CPF)) {
                        edtCpf.setError(barberException.getMessage());
                    } else if (barberException.getComponent().equals(BarberException.EMAIL)) {
                        edtEmail.setError(barberException.getMessage());
                    } else if (barberException.getComponent().equals(BarberException.SENHA)) {
                        edtSenha.setError(barberException.getMessage());
                    }
                }
            } catch (BarberException listException) {
                setError(listException.getMessage());
            } finally {
                setButtons(true);
            }
        }
    }

    private void setError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setButtons(boolean state) {
        btnRegistrar.setEnabled(state);
        btnLogin.setEnabled(state);
    }

}
