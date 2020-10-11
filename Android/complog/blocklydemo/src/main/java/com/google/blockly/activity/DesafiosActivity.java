package com.google.blockly.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.blockly.android.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import adapters.DesafioAdapter;
import model.Desafio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesafiosActivity extends AppCompatActivity {
    ArrayList<Desafio> desafios;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDesafios);
        toolbar.setTitle("Desafios");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lista = (ListView) findViewById(R.id.lvDesafios);

        desafios = new ArrayList<Desafio>();

        if (!(this.isNetworkAvailable())) {
            this.desafiosPadrao();

        } else {
            this.httpRequest();
        }
    }

    private void alterarDesafios() {
        ArrayAdapter adapter = new DesafioAdapter(this, desafios);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Desafio atual = DesafiosActivity.this.desafios.get(position);

                Intent intent = new Intent(DesafiosActivity.this, DesafioDetalhesActivity.class);

                intent.putExtra("desafio", atual);

                startActivity(intent);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void JsonParaObjeto(String resource) {
        //Toast.makeText(this.getApplicationContext(), resource, Toast.LENGTH_LONG).show();

        if (resource.contains("Nada encontrado no Banco de Dados")) {
            this.desafiosPadrao();

        } else {
            try {
                JSONArray array = new JSONArray(resource);

                Log.i("Array", array.toString());

                for(int i = 0; i < array.length(); i++){
                    JSONObject desafio = (JSONObject) array.get(i);

                    desafios.add(new Desafio(desafio.getInt("id"), desafio.getString("nome"), desafio.getString("counteudo"), desafio.getString("autor"), desafio.getInt("nivel")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.alterarDesafios();
        }
    }

    private void httpRequest() {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.31.131/complogAPI/desafio/read.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DesafiosActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DesafiosActivity.this.desafiosPadrao();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    DesafiosActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JsonParaObjeto(myResponse);
                        }
                    });
                } else {
                    DesafiosActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DesafiosActivity.this.desafiosPadrao();
                        }
                    });
                }
            }
        });

    }


    public void desafiosPadrao() {
        desafios.add(new Desafio(0, "Drift", "Tente manter o robô andando em uma diagonal, porém sem parar definitivamente.", "Mateus Orlandin Lorenzatti", 2));
        desafios.add(new Desafio(1, "Mantenha-se no meio", "Crie uma linha e coloque obstáculos sobre ela. Você deverá começar e terminar em cima desta linha.", "Felipe Martin Sampaio", 2));
        desafios.add(new Desafio(2, "Zigue Zague", "Faça o robô andar de um lado para o outro, oscliando em 90 graus.", "Jamilton Damasceno", 1));
        desafios.add(new Desafio(3, "Vá até parar", "Ande até que encontre um objeto. Quando encontrar tente escapar de uma possível colisão.", "Andres Menendez", 1));
        desafios.add(new Desafio(4, "Labirinto", "Monte um labirinto com caixas ou classes e crie um código para que o robô encontre a saída!", "Lucas Lutz", 3));

        this.alterarDesafios();
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
