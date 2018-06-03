package com.github.mavbraz.barbermobile.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;

import java.util.List;
import java.util.Map;

public class BarberHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public BarberHelper(Context context) {
        super(context, "Barber", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /* // "Config -> id(auto_increment), key (not null unique), value(not null) => 1, nome, Matheus Braz => 2, email, mavbraz@gmail.com"
        String sql = "CREATE TABLE Config (id INTEGER PRIMARY KEY AUTOINCREMENT, chave TEXT NOT NULL UNIQUE, valor TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*String sql = "DROP TABLE IF EXISTS Cliente;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);*/
    }

    /*public void insert(Map<String, String> value) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getData(cliente);

        db.insert("Student", null, dados);
    }

    private ContentValues getData(Map<String, String> value) {
        ContentValues dados = new ContentValues();
        dados.put("nome", value..getNome());
        dados.put("email", cliente.getEmail());
        dados.put("token", cliente.getToken());
        return dados;
    }

    public void remove(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(cliente.getId())};
        db.delete("Cliente", "id = ?", params);
    }

    public List<Map<String, String>> getData() {
        return null;
    }

    public void update(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDataCliente(cliente);

        String [] params = {String.valueOf(cliente.getId())};
        db.update("Cliente", dados, "id = ?", params);
    }*/


}
