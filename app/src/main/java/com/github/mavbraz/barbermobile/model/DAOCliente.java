package com.github.mavbraz.barbermobile.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mavbraz.barbermobile.model.basicas.Cliente;

import java.util.List;

public class DAOCliente extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DAOCliente(Context context) {
        super(context, "Barber", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Cliente (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, " +
                " token TEXT, email TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Cliente;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDataCliente(cliente);

        db.insert("Student", null, dados);
    }

    private ContentValues getDataCliente(Cliente cliente) {
        ContentValues dados = new ContentValues();
        dados.put("nome", cliente.getNome());
        dados.put("email", cliente.getEmail());
        dados.put("token", cliente.getToken());
        return dados;
    }

    public void remove(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(cliente.getId())};
        db.delete("Cliente", "id = ?", params);
    }

    public List<Cliente> getClientes() {
        return null;
    }

    public void update(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDataCliente(cliente);

        String [] params = {String.valueOf(cliente.getId())};
        db.update("Cliente", dados, "id = ?", params);
    }


}
