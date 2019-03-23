package com.jonasrosendo.tarefas.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jonasrosendo.tarefas.R;
import com.jonasrosendo.tarefas.helper.TarefaDAO;
import com.jonasrosendo.tarefas.model.Tarefa;

public class AddTarefaActivity extends AppCompatActivity {

    private TextInputEditText edt_tarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);

        edt_tarefa = findViewById(R.id.edt_add_tarefa);

        //recuperar tarefa se for edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefa");
        if (tarefaAtual != null){
            edt_tarefa.setText(tarefaAtual.getNome());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_salvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null){
                    String text_tarefa = edt_tarefa.getText().toString().trim();
                    if(!text_tarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNome(text_tarefa);
                        tarefa.setId(tarefaAtual.getId());
                        if (tarefaDAO.updateTarefa(tarefa)){
                            finish();
                            Toast.makeText(this, "Sucesso ao atualizar!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                }else{
                    String text_tarefa = edt_tarefa.getText().toString().trim();
                    if(!text_tarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNome(text_tarefa);
                        tarefaDAO.addTarefa(tarefa);
                        finish();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
