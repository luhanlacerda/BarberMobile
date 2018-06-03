package com.github.mavbraz.barbermobile.view;

import android.content.Intent;
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

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    EditText edtEmail;
    EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.email);
        edtSenha = findViewById(R.id.password);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);

        edtSenha.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            logarCliente();
        } else if (view.getId() == R.id.btn_signup) {
            startActivity(new Intent(this, SingupActivity.class));
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == edtSenha && EditorInfo.IME_ACTION_DONE == actionId) {
            logarCliente();

            return true;
        }

        return false;
    }

    private void logarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setEmail(edtEmail.getText().toString());
            cliente.setSenha(edtSenha.getText().toString());

            if (new NegocioCliente().login(cliente)) {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(this, listException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (NoSuchAlgorithmException senhaException) {
            Toast.makeText(this, "Erro interno ao criptografar a senha", Toast.LENGTH_SHORT).show();
        }
    }

}
