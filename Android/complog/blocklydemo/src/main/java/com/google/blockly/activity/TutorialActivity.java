package com.google.blockly.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.blockly.android.demo.BlocklyActivity;
import com.google.blockly.android.demo.R;

public class TutorialActivity extends AppCompatActivity {

    // Componentes da view
    private TextView texto;
    private ImageView imagem;
    private ImageView seta_dr, seta_es;
    private ProgressBar barra;
    private Button abrirBlockly;

    // Vetores e auxiliares das páginas
    private int[] imagens = {R.drawable.tutorial_1, R.drawable.tutorial_2, R.drawable.tutorial_3, R.drawable.tutorial_4};
    private String[] textos = {
            "O CompLog permite a criação de códigos de forma intuitiva através deste app" +
            " e o envio para um robô, que irá executar as instruções, detectando obstáculos no caminho.",
            "O CompLog utiliza a biblioteca Blockly, criada pelo Google for Education, para a criação" +
            " de códigos em blocos, possibilitando uma maneira didática de aprender e praticar a lógica de programação.",
            "O CompLog disponibiliza uma comunidade onde usuários possam, através de suas instituições, postar desafios" +
            " para serem resolvidos por qualquer usuário. Mas não se preocupe, pois existem diferentes níveis de dificuldades! ",
            "O Blockly pemite que blocos, como componentes lógicos, sejam inseridos em um" +
            " workspace apenas pressionando e arrastando para a posição em ordem de sua preferência. Você ainda pode salvar"+
            " e carregar o último código construído, mas não se preoucupe, pois sempre que você sai do app o código continua lá." +
            " Está pronto para começar? "};
    private int paginaAtual = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tutorial");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.inicializarComponentes();
        this.atualizaView();

        this.paginaAtual = 0;
    }

    private void inicializarComponentes() {
        this.texto = (TextView) findViewById(R.id.tutorial_texto);
        this.imagem = (ImageView) findViewById(R.id.tutorial_imagem);

        this.seta_dr = (ImageView) findViewById(R.id.tutorial_botao_direita);
        this.seta_dr.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                paginaAtual++;
                atualizaView();
            }
        });

        this.seta_es = (ImageView) findViewById(R.id.tutorial_botao_esquerda);
        this.seta_es.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                paginaAtual--;
                atualizaView();
            }
        });

        this.barra = (ProgressBar) findViewById(R.id.barra_progresso);
        this.abrirBlockly = (Button) findViewById(R.id.tutorial_botao_final);
        this.abrirBlockly.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, BlocklyActivity.class);
                intent.putExtra("limpar" , true);
                intent.putExtra("back", "abertura");

                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void atualizaView() {
        this.texto.setText(this.textos[paginaAtual]);

        this.imagem.setImageDrawable(getResources().getDrawable(this.imagens[this.paginaAtual]));

        switch (paginaAtual) {
            case 0:
                this.seta_es.setVisibility(View.GONE);
                this.seta_dr.setVisibility(View.VISIBLE);
                this.abrirBlockly.setVisibility(View.VISIBLE);

                this.barra.setProgress(25, true);
                break;

            case 1:
                this.seta_es.setVisibility(View.VISIBLE);
                this.seta_dr.setVisibility(View.VISIBLE);
                this.abrirBlockly.setVisibility(View.VISIBLE);

                this.barra.setProgress(50, true);
                break;

            case 2:
                this.seta_es.setVisibility(View.VISIBLE);
                this.seta_dr.setVisibility(View.VISIBLE);
                this.abrirBlockly.setVisibility(View.VISIBLE);

                this.barra.setProgress(75, true);
                break;

            case 3:
                this.seta_es.setVisibility(View.VISIBLE);
                this.seta_dr.setVisibility(View.GONE);
                this.abrirBlockly.setVisibility(View.VISIBLE);

                this.barra.setProgress(100, true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, AberturaActivity.class));
                finishAffinity();
            default:
                break;
        }
        return true;
    }

}

