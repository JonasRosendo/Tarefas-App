package com.jonasrosendo.tarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jonasrosendo.tarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase db_write;
    private SQLiteDatabase db_read;

    public TarefaDAO(Context context) {
        DbHelper helper = new DbHelper(context);
        db_write = helper.getWritableDatabase();
        db_read = helper.getReadableDatabase();
    }

    @Override
    public boolean addTarefa(Tarefa tarefa) {

        try{
            ContentValues values = new ContentValues();
            values.put("nome", tarefa.getNome());

            db_write.insert(DbHelper.TABELA_TAREFAS, null, values);

            Log.i("DB_INFO", "Tarefa salva com sucesso");
        }catch (Exception e){

            Log.i("DB_INFO", "Erro ao gravar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean updateTarefa(Tarefa tarefa) {

        try{
            String[] args = {String.valueOf(tarefa.getId())};
            ContentValues values = new ContentValues();
            values.put("nome", tarefa.getNome());
            db_write.update(DbHelper.TABELA_TAREFAS, values, "id=?", args);

            Log.i("DB_INFO", "Tarefa atualizada com sucesso");
        }catch (Exception e){

            Log.i("DB_INFO", "Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean deleteTarefa(Tarefa tarefa) {

        try{
            String[] args = {String.valueOf(tarefa.getId())};
            db_write.delete(DbHelper.TABELA_TAREFAS, "id=?", args);

            Log.i("DB_INFO", "Tarefa removida com sucesso");
        }catch (Exception e){

            Log.i("DB_INFO", "Erro ao remover tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + ";";
        Cursor cursor = db_read.rawQuery(sql,null);

        cursor.moveToFirst();
        while (cursor.moveToNext()){

            Tarefa tarefa = new Tarefa();

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            tarefa.setId(id);
            tarefa.setNome(nome);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
