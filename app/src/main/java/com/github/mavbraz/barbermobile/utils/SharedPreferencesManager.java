package com.github.mavbraz.barbermobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String APP_NAME = "barber";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, value);
        editor.apply();
    }

    public void saveEmail(String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, value);
        editor.apply();
    }

    public void removeToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    public void removeEmail() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public boolean isLogged() {
        return this.getToken() != null && this.getEmail() != null;
    }

}
