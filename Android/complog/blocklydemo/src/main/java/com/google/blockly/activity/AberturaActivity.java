package com.google.blockly.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.blockly.android.demo.R;
import com.google.blockly.android.demo.BlocklyActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import helpers.MQTTAbstract;

public class AberturaActivity extends MQTTAbstract {
    private TextView conexao, getStarted, desafios;
    private ImageView abrir, info;

    private int estadoConexao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        this.inicializarComponentes();

        this.inicializarConexao();
    }

    private void inicializarConexao() {
        if (this.isNetworkAvailable()) {
            this.conectar();

        } else {
            this.estadoConexao = 3;
        }

        this.atualizarConexaoView();
    }

    private void setConexao() {
        if (this.isConexao_estabelecida()) {
            this.setCallback();

            this.estadoConexao = 1;

            try {
                this.enviaChecagem();
                this.inscrever();
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            this.estadoConexao = 3;
        }

        this.atualizarConexaoView();
    }

    private void inicializarComponentes() {
        abrir = (ImageView) findViewById(R.id.botao_abrir);
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AberturaActivity.this, BlocklyActivity.class);

                intent.putExtra("conexao", AberturaActivity.this.isPlataforma_conectada());
                intent.putExtra("limpar", false);

                intent.putExtra("back", "abertura");

                startActivity(intent);
            }
        });

        getStarted = (TextView) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AberturaActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        conexao = (TextView) findViewById(R.id.text_conexao);

        this.conexao.setBackgroundColor(Color.GRAY);
        this.conexao.setText("Ainda não conectado. Clique aqui para conectar.");

        conexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (estadoConexao) {
                    case 0:
                        AberturaActivity.this.setConexao();
                        break;

                    case 1:
                        try {
                            AberturaActivity.this.enviaChecagem();
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        break;

                    case 3:
                        AberturaActivity.this.inicializarConexao();
                        break;
                }
            }
        });

        this.desafios = (TextView) findViewById(R.id.text_desafios);
        desafios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AberturaActivity.this, DesafiosActivity.class));
            }
        });

        this.info = (ImageView) findViewById(R.id.image_info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AberturaActivity.this, InfoActivity.class));
            }
        });
    }

    private void atualizarConexaoView() {
        switch (this.estadoConexao) {
            case 1:
                this.conexao.setBackgroundColor(Color.parseColor("#356ecc"));
                conexao.setText("Conectado no servidor. Clique aqui para confirmar o robô.");
                break;

            case 2:
                this.conexao.setBackgroundColor(Color.parseColor("#9fa801"));
                conexao.setText("O robô está só esperando o código!");
                break;

            case 3:
                this.conexao.setBackgroundColor(Color.parseColor("#ba2727"));
                conexao.setText("Verifique sua conexão com a internet.");
                break;
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void setCallback() {
        this.cliente.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                if (mqttMessage.toString().equals("conectado")) {
                    AberturaActivity.this.plataforma_conectada = true;
                    AberturaActivity.this.estadoConexao = 2;
                    AberturaActivity.this.atualizarConexaoView();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }
}

