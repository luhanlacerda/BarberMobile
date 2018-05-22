package com.example.vitoroliveira.barber.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.vitoroliveira.barber.R;
import com.example.vitoroliveira.barber.api.ApiHttp;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ApiHttp api = new ApiHttp();
        api.login("mavbraz@barbershop.com", "8D6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918", this, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), new JSONObject(response)
                                            .getString("token")
                                    , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "No json found.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();

                        /*if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //Toast.makeText(context,
                            //        context.getString(R.string.error_network_timeout),
                            //        Toast.LENGTH_LONG).show();
                            Toast.makeText(this, "Timeouterror or noconnectionerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(this, "authfailureerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(this, "servererror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(this, "networkerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(this, "parseerror", Toast.LENGTH_LONG).show();
                        }*/
                    }
                });
        api.register("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBiYXJiZXJzaG9wLmNvbSIsImlzcyI6ImJhcmJlcnNob3AuY29tIiwicm9sZSI6IkZVTkNJT05BUklPIiwiaWF0IjoxNTI2NTIwMjExfQ.jZJE4wZtQzv3BEijHV0WjNZO9bQrZUGOLmWfoiOonL0",
                "mavbraz3@barbershop.com", "8A6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918", "CLIENTE", this,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), new JSONObject(response)
                                            .getString("message")
                                    , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "No json found.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();

                        /*if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //Toast.makeText(context,
                            //        context.getString(R.string.error_network_timeout),
                            //        Toast.LENGTH_LONG).show();
                            Toast.makeText(this, "Timeouterror or noconnectionerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(this, "authfailureerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(this, "servererror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(this, "networkerror", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(this, "parseerror", Toast.LENGTH_LONG).show();
                        }*/
                    }
                });
    }

}
