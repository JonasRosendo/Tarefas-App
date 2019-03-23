package com.jonasrosendo.tarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DB_NOME = "db_tarefas";
    public static final String TABELA_TAREFAS = "tarefas";

    public DbHelper(Context context) {
        super(context, DB_NOME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL);";

        try {

            db.execSQL(sql);
            Log.i("DB_INFO", "Sucesso ao criar tabela ");
        }catch (Exception e){
            Log.i("DB_INFO", "Erro ao criar tabela " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
