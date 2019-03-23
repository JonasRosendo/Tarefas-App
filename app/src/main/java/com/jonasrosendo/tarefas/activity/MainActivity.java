package com.jonasrosendo.tarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jonasrosendo.tarefas.R;
import com.jonasrosendo.tarefas.helper.DbHelper;
import com.jonasrosendo.tarefas.helper.RecyclerItemClickListener;
import com.jonasrosendo.tarefas.adapter.TarefasAdapter;
import com.jonasrosendo.tarefas.helper.TarefaDAO;
import com.jonasrosendo.tarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerTarefas;
    private TarefasAdapter tarefasAdapter;
    private List<Tarefa> tarefaList = new ArrayList<>();
    private Tarefa tarefaSelecionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerTarefas = findViewById(R.id.recyclerListaTarefas);

        recyclerTarefas.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerTarefas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Tarefa tarefa = tarefaList.get(position);

                Intent intent = new Intent(MainActivity.this, AddTarefaActivity.class);
                intent.putExtra("tarefa", tarefa);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                tarefaSelecionada = tarefaList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Atenção!!!");
                builder.setMessage("Deseja excluir tarefa: " + tarefaSelecionada.getNome());
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                        if(tarefaDAO.deleteTarefa(tarefaSelecionada)){
                            carregarTarefas();
                            Toast.makeText(MainActivity.this, "Sucesso ao excluir tarefa", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Erro ao excluir tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create();
                builder.show();


            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTarefas();
    }

    public void carregarTarefas(){
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefaList = tarefaDAO.listar();

        tarefasAdapter = new TarefasAdapter(tarefaList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerTarefas.setLayoutManager(layoutManager);
        recyclerTarefas.setHasFixedSize(true);
        recyclerTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        recyclerTarefas.setAdapter(tarefasAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
