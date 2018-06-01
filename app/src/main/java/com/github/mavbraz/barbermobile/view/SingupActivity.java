package com.github.mavbraz.barbermobile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mavbraz.barbermobile.R;

public class SingupActivity extends AppCompatActivity implements TextView.OnEditorActionListener {


    EditText edtNome;
    ///EditText edtCpf;
    EditText edtEmail;
    EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        final EditText edtCpf = (EditText) findViewById(R.id.edt_cpf);

        edtCpf.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;
            String old = "";

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
                    }else if (str.length() > 3){
                        str = str.substring(0,3) + '.' + str.substring(3);
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

        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtSenha = (EditText) findViewById(R.id.edt_password);
        edtSenha.setOnEditorActionListener(this);

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == edtSenha && EditorInfo.IME_ACTION_DONE == actionId) {
            String nome = edtNome.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            boolean ok = true;

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError(getString(R.string.msg_erro_email));
                ok = false;
            }

            if (!senha.equals("123")) {
                edtSenha.setError(getString(R.string.msg_erro_senha));
                ok = false;
            }

            if (ok) {
                Toast.makeText(this, getString(R.string.msg_sucesso, nome, email), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }
}
