package com.github.mavbraz.barbermobile.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Pagamento;
import com.github.mavbraz.barbermobile.model.basicas.Servico;
import com.github.mavbraz.barbermobile.model.basicas.Situacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BarberSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbBarber";
    private static final int VERSAO_BANCO = 1;

    private static final String TABELA_AGENDAMENTO = "agendamento";
    private static final String TABELA_SERVICO = "servico";
    private static final String TABELA_AGENDAMENTO_SERVICO = "agendamento_servico";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_VALOR = "valor";
    private static final String COLUNA_HORARIO = "horario";
    private static final String COLUNA_SITUACAO = "situacao";
    private static final String COLUNA_PAGAMENTO = "pagamento";
    private static final String COLUNA_AGENDAMENTO_ID = "agendamento_id";
    private static final String COLUNA_SERVICO_ID = "servico_id";

    public BarberSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABELA_SERVICO + "(" +
                        COLUNA_ID + " INTEGER PRIMARY KEY, " +
                        COLUNA_NOME + " VARCHAR(50) NOT NULL, " +
                        COLUNA_DESCRICAO + " VARCHAR(255) NOT NULL, " +
                        COLUNA_VALOR + " DECIMAL(15,2) NOT NULL);"
        );
        db.execSQL(
                "CREATE TABLE " + TABELA_AGENDAMENTO + "(" +
                        COLUNA_ID + " INTEGER PRIMARY KEY, " +
                        COLUNA_HORARIO + " VARCHAR(10) NOT NULL, " +
                        COLUNA_SITUACAO + " VARCHAR(30) NOT NULL DEFAULT 'MARCADO', " +
                        COLUNA_PAGAMENTO + " VARCHAR(30) NOT NULL DEFAULT 'PENDENTE', " +
                        "CHECK (" + COLUNA_SITUACAO + " = 'MARCADO' OR " +
                        COLUNA_SITUACAO + " = 'REALIZADO' OR " +
                        COLUNA_SITUACAO + " = 'CANCELADO'), " +
                        "CHECK (" + COLUNA_PAGAMENTO + " = 'PENDENTE' OR " +
                        COLUNA_PAGAMENTO + " = 'REALIZADO'));"
        );
        db.execSQL(
                "CREATE TABLE " + TABELA_AGENDAMENTO_SERVICO + "(" +
                        COLUNA_AGENDAMENTO_ID + " INTEGER NOT NULL, " +
                        COLUNA_SERVICO_ID + " INTEGER NOT NULL, " +
                        "PRIMARY KEY(" + COLUNA_AGENDAMENTO_ID + ", " + COLUNA_SERVICO_ID + "), " +
                        "FOREIGN KEY(" + COLUNA_AGENDAMENTO_ID + ") REFERENCES " + TABELA_AGENDAMENTO + "(" + COLUNA_ID + "), " +
                        "FOREIGN KEY(" + COLUNA_SERVICO_ID + ") REFERENCES " + TABELA_SERVICO + "(" + COLUNA_ID + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_AGENDAMENTO_SERVICO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_AGENDAMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_SERVICO);
        onCreate(db);
    }

    @SuppressWarnings("EmptyCatchBlock")
    private void inserirServico(Servico servico) {
        try {
            if (!hasServico(servico)) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(COLUNA_ID, servico.getId());
                values.put(COLUNA_NOME, servico.getNome());
                values.put(COLUNA_DESCRICAO, servico.getDescricao());
                values.put(COLUNA_VALOR, servico.getValor());

                db.insert(TABELA_SERVICO, null, values);
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    private void inserirAgendamento(Agendamento agendamento) {
        try {
            if (!hasAgendamento(agendamento)) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(COLUNA_ID, agendamento.getId());
                values.put(COLUNA_HORARIO, agendamento.getHorario());
                values.put(COLUNA_SITUACAO, agendamento.getSituacao().name());
                values.put(COLUNA_PAGAMENTO, agendamento.getPagamento().name());

                db.insert(TABELA_AGENDAMENTO, null, values);
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    private void inserirAgendamentoServico(Agendamento agendamento, Servico servico) {
        try {
            if (!hasAgendamentoServico(agendamento, servico)) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(COLUNA_AGENDAMENTO_ID, agendamento.getId());
                values.put(COLUNA_SERVICO_ID, servico.getId());

                db.insert(TABELA_AGENDAMENTO_SERVICO, null, values);
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasServico(Servico servico) {
        boolean found = false;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_SERVICO,
                new String[]{COLUNA_ID},
                COLUNA_ID + " = ?",
                new String[]{String.valueOf(servico.getId())},
                null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                found = true;
            }

            cursor.close();
        }

        db.close();

        return found;
    }

    private boolean hasAgendamento(Agendamento agendamento) {
        boolean found = false;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_AGENDAMENTO,
                new String[]{COLUNA_ID},
                COLUNA_ID + " = ?",
                new String[]{String.valueOf(agendamento.getId())},
                null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                found = true;
            }

            cursor.close();
        }

        db.close();

        return found;
    }

    private boolean hasAgendamentoServico(Agendamento agendamento, Servico servico) {
        boolean found = false;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_AGENDAMENTO_SERVICO,
                new String[]{COLUNA_AGENDAMENTO_ID},
                COLUNA_AGENDAMENTO_ID + " = ? AND " + COLUNA_SERVICO_ID + " = ?",
                new String[]{String.valueOf(agendamento.getId()), String.valueOf(servico.getId())},
                null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                found = true;
            }

            cursor.close();
        }

        db.close();

        return found;
    }

    public void sincronizarAgendamentos(List<Agendamento> agendamentos) {
        for (Servico servico : todosServicosDistintos(agendamentos)) {
            inserirServico(servico);
        }

        for (Agendamento agendamento : agendamentos) {
            inserirAgendamento(agendamento);

            for (Servico servico : agendamento.getServicos()) {
                inserirAgendamentoServico(agendamento, servico);
            }
        }
    }

    public List<Agendamento> carregarAgendamentos() {
        Map<Integer, Agendamento> agendamentos = new HashMap<>();

        String selectQuery = String.format("SELECT %s as '%s', %s as '%s', %s as '%s', %s as '%s'," +
                        " %s as '%s', %s as '%s', %s as '%s', %s as '%s'" +
                        " FROM " + TABELA_AGENDAMENTO +
                        " INNER JOIN " + TABELA_AGENDAMENTO_SERVICO +
                        " ON " + gerarTabelaColuna(TABELA_AGENDAMENTO_SERVICO, COLUNA_AGENDAMENTO_ID) +
                        " = " + gerarTabelaColuna(TABELA_AGENDAMENTO, COLUNA_ID) +
                        " INNER JOIN " + TABELA_SERVICO +
                        " ON " + gerarTabelaColuna(TABELA_SERVICO, COLUNA_ID) +
                        " = " + gerarTabelaColuna(TABELA_AGENDAMENTO_SERVICO, COLUNA_SERVICO_ID),
                gerarTabelaColuna(TABELA_AGENDAMENTO, COLUNA_ID),
                "agendamento_id",
                gerarTabelaColuna(TABELA_AGENDAMENTO, COLUNA_HORARIO),
                "agendamento_horario",
                gerarTabelaColuna(TABELA_AGENDAMENTO, COLUNA_SITUACAO),
                "agendamento_situacao",
                gerarTabelaColuna(TABELA_AGENDAMENTO, COLUNA_PAGAMENTO),
                "agendamento_pagamento",
                gerarTabelaColuna(TABELA_SERVICO, COLUNA_ID),
                "servico_id",
                gerarTabelaColuna(TABELA_SERVICO, COLUNA_NOME),
                "servico_nome",
                gerarTabelaColuna(TABELA_SERVICO, COLUNA_DESCRICAO),
                "servico_descricao",
                gerarTabelaColuna(TABELA_SERVICO, COLUNA_VALOR),
                "servico_valor");


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            int agendamentoId = cursor.getInt(cursor.getColumnIndex("agendamento_id"));

            Servico servico = new Servico(
                    cursor.getInt(cursor.getColumnIndex("servico_id")),
                    cursor.getString(cursor.getColumnIndex("servico_nome")),
                    cursor.getString(cursor.getColumnIndex("servico_descricao")),
                    cursor.getDouble(cursor.getColumnIndex("servico_valor"))
            );

            if (agendamentos.containsKey(agendamentoId)) {
                Agendamento agendamento = agendamentos.get(agendamentoId);
                agendamento.getServicos().add(servico);
            } else {
                List<Servico> servicos = new ArrayList<>();
                servicos.add(servico);
                Agendamento agendamento = new Agendamento(
                        agendamentoId,
                        cursor.getLong(cursor.getColumnIndex("agendamento_horario")),
                        Situacao.valueOf(cursor.getString(cursor.getColumnIndex("agendamento_situacao"))),
                        Pagamento.valueOf(cursor.getString(cursor.getColumnIndex("agendamento_pagamento"))),
                        null,
                        servicos
                );
                agendamentos.put(agendamentoId, agendamento);
            }
        }

        cursor.close();
        db.close();

        return sortAgendamentos(agendamentos);
    }

    private List<Agendamento> sortAgendamentos(Map<Integer, Agendamento> map) {
        List<Agendamento> agendamentos = new ArrayList<>(map.values());
        Collections.sort(agendamentos, new Comparator<Agendamento>() {
            public int compare(Agendamento a1, Agendamento a2) {
                return a1.getId() - a2.getId();
            }
        });

        return agendamentos;
    }

    private Set<Servico> todosServicosDistintos(List<Agendamento> agendamentos) {
        Set<Servico> servicos = new HashSet<>();

        for (Agendamento agendamento : agendamentos) {
            servicos.addAll(agendamento.getServicos());
        }

        return servicos;
    }

    private String gerarTabelaColuna(String tabela, String coluna) {
        return tabela + "." + coluna;
    }

}
