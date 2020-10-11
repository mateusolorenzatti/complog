package com.google.blockly.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.blockly.android.demo.BlocklyActivity;
import com.google.blockly.android.demo.R;

import model.Desafio;

public class DesafioDetalhesActivity extends AppCompatActivity {
    private Desafio desafio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_detalhes);

        this.desafio = (Desafio) getIntent().getExtras().getSerializable("desafio");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDesafioDetalhe);
        toolbar.setTitle(desafio.getTitulo());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView autor = (TextView) findViewById(R.id.autor_desafio);
        autor.setText("Feito por: " + this.desafio.getAutor());

        TextView conteudo = (TextView) findViewById(R.id.conteudo_desafio);
        conteudo.setText(this.desafio.getConteudo());

        Button botao_abrir = (Button) findViewById(R.id.botao_desafio);
        botao_abrir.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesafioDetalhesActivity.this, BlocklyActivity.class);

                intent.putExtra("limpar", true);
                intent.putExtra("back", "desafio");

                startActivity(intent);
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }
}
