package com.jonasrosendo.tarefas.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonasrosendo.tarefas.R;
import com.jonasrosendo.tarefas.model.Tarefa;

import java.util.List;

public class TarefasAdapter extends RecyclerView.Adapter<TarefasAdapter.MyViewHolder> {

    List<Tarefa> tarefaList;

    public TarefasAdapter(List<Tarefa> tarefaList) {
        this.tarefaList = tarefaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_tarefas_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Tarefa tarefa = tarefaList.get(i);
        holder.txv_tarefa.setText(tarefa.getNome());

    }

    @Override
    public int getItemCount() {
        return this.tarefaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txv_tarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_tarefa = itemView.findViewById(R.id.txv_tarefa);
        }
    }
}
