package com.example.vitoroliveira.barber.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitoroliveira.barber.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class ApiHttp {

    private final String basePath = "http://192.168.0.104:3000";

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
