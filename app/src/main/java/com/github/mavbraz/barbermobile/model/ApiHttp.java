package com.github.mavbraz.barbermobile.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiHttp {

    /*
    EXAMPLE
    com.example.vitoroliveira.barber.api.ApiHttp api = new com.example.vitoroliveira.barber.api.ApiHttp();
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

                        /if (error instanceof TimeoutError || error instanceof NoConnectionError) {
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
                        }/
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

                        /if (error instanceof TimeoutError || error instanceof NoConnectionError) {
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
                        }/
        }
    });*/

    private final String basePath = "http://127.0.0.1:3000";

    public void login(final String email, final String password, Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, basePath + "/login", listener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("senha", password);

                return params;
            }
        };

        queue.add(request);
    }

    public void register(final String authorization, final String email, final String password, final String cargo, Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, basePath + "/register", listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", authorization);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("senha", password);
                params.put("cargo", cargo);

                return params;
            }
        };

        queue.add(request);
    }
}
