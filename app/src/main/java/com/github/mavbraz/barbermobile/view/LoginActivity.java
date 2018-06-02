package com.github.mavbraz.barbermobile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioCliente;
import com.github.mavbraz.barbermobile.model.basicas.Cliente;
import com.github.mavbraz.barbermobile.utils.BarberException;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtEmail;
    TextView txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.email);
        txtSenha = findViewById(R.id.password);

        findViewById(R.id.btn_login).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            try {
                Cliente cliente = new Cliente();
                cliente.setEmail(txtEmail.getText().toString());
                cliente.setSenha(txtSenha.getText().toString());

                if (new NegocioCliente().login(cliente)) {
                    Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            } catch (BarberException loginException) {
                try {
                    for (BarberException barberException : loginException.getExceptions()) {
                        if (barberException.getComponent().equals(BarberException.EMAIL)) {
                            txtEmail.setError(barberException.getMessage());
                        } else if (barberException.getComponent().equals(BarberException.SENHA)) {
                            txtSenha.setError(barberException.getMessage());
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
}
