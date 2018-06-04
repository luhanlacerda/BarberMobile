package com.github.mavbraz.barbermobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SingupActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    EditText edtNome;
    EditText edtCpf;
    EditText edtEmail;
    EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        edtCpf = findViewById(R.id.edt_cpf);
        edtNome = findViewById(R.id.edt_nome);
        edtEmail = findViewById(R.id.edt_email);
        edtSenha = findViewById(R.id.edt_password);

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

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

            return true;
        }

        return false;
    }

    private void registrarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNome(edtNome.getText().toString());
            cliente.setCpf(edtCpf.getText().toString());
            cliente.setEmail(edtEmail.getText().toString());
            cliente.setSenha(edtSenha.getText().toString());

            if (new NegocioCliente().insert(cliente)) {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(this, listException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (NoSuchAlgorithmException senhaException) {
            Toast.makeText(this, "Erro interno ao criptografar a senha", Toast.LENGTH_SHORT).show();
        }
    }

}
