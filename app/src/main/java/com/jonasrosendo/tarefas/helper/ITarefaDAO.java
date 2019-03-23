package com.jonasrosendo.tarefas.helper;

import com.jonasrosendo.tarefas.model.Tarefa;

import java.util.List;

public interface ITarefaDAO {

    public boolean addTarefa(Tarefa tarefa);
    public  boolean updateTarefa(Tarefa tarefa);
    public boolean deleteTarefa(Tarefa tarefa);
    public List<Tarefa> listar();
}
