package com.github.mavbraz.barbermobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BarberSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbBarber";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_AGENDAMENTO = "agendamento";
    public static final String TABELA_SERVICO = "servico";
    public static final String TABELA_AGENDAMENTO_SERVICO = "agendamento_servico";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_VALOR = "valor";
    public static final String COLUNA_HORARIO = "horario";
    public static final String COLUNA_SITUACAO = "situacao";
    public static final String COLUNA_PAGAMENTO = "pagamento";
    public static final String COLUNA_AGENDAMENTO_ID = "agendamento_id";
    public static final String COLUNA_SERVICO_ID = "servico_id";

    public BarberSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELA_SERVICO + "(" +
                        COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_NOME + " VARCHAR(50) NOT NULL, " +
                        COLUNA_DESCRICAO + " VARCHAR(255) NOT NULL, " +
                        COLUNA_VALOR + " DECIMAL(15,2) NOT NULL);"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELA_AGENDAMENTO + "(" +
                        COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_HORARIO + " VARCHAR(10) NOT NULL, " +
                        COLUNA_SITUACAO + " VARCHAR(30) NOT NULL DEFAULT 'MARCADO', " +
                        COLUNA_PAGAMENTO + " VARCHAR(30) NOT NULL DEFAULT 'PENDENTE', " +
                        "CHECK (" + COLUNA_SITUACAO + " = 'MARCADO' OR " +
                        COLUNA_SITUACAO + " = 'REALIZADO' OR " +
                        COLUNA_SITUACAO + " = 'CANCELADO'), " +
                        "CHECK (" + COLUNA_PAGAMENTO + " = 'PENDENTE' OR " +
                        COLUNA_PAGAMENTO + " = 'REALIZADO'));"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELA_AGENDAMENTO_SERVICO + "(" +
                        COLUNA_AGENDAMENTO_ID + " INTEGER NOT NULL, " +
                        COLUNA_SERVICO_ID + " INTEGER NOT NULL, " +
                        "PRIMARY KEY(" + COLUNA_AGENDAMENTO_ID + ", " + COLUNA_SERVICO_ID + "), " +
                        "FOREIGN KEY(" + COLUNA_AGENDAMENTO_ID + ") REFERENCES " + TABELA_AGENDAMENTO + "(" + COLUNA_ID + "), " +
                        "FOREIGN KEY(" + COLUNA_SERVICO_ID + ") REFERENCES " + TABELA_SERVICO + "(" + COLUNA_ID + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_AGENDAMENTO_SERVICO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_AGENDAMENTO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_SERVICO);
        onCreate(sqLiteDatabase);
    }

}
