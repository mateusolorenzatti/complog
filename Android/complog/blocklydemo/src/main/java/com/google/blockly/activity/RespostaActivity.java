package com.google.blockly.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.blockly.android.demo.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import helpers.MQTTAbstract;
import helpers.Montador;

public class RespostaActivity extends MQTTAbstract {
    private int qos = 0;

    private Button cancelar;
    private String code;
    private TextView distancia;
    private ListView respostas;

    private String[] mensagensTerminal = {"Clique no código para enviar"};

    private ArrayAdapter<String> ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        toolbar.setTitle("Resposta");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.code = getIntent().getExtras().getString("codigo");

        inicializarComponentes();

        this.conectar();
    }

    private void inicializarComponentes() {
        cancelar = (Button) findViewById(R.id.botao_cancelar_resposta);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RespostaActivity.this.cancelar();
            }
        });

        TextView codigo = (TextView) findViewById(R.id.codigo_text);
        codigo.setText(this.code);

        codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RespostaActivity.this.inscrever();

                    RespostaActivity.this.publicar(Montador.montarCodigo(RespostaActivity.this.code));

                    RespostaActivity.this.setCallback();
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        distancia = (TextView) findViewById(R.id.distancia_view);

        respostas = (ListView) findViewById(R.id.list_respostas);

        ad = new ArrayAdapter<String>(
                this.getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mensagensTerminal);

        respostas.setAdapter( ad );
    }

    private void atualizaTerminal(String mensagem) {
        this.mensagensTerminal = Arrays.copyOf(mensagensTerminal, mensagensTerminal.length + 1);
        mensagensTerminal[mensagensTerminal.length - 1] = mensagem;

        ad = new ArrayAdapter<String>(
                this.getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mensagensTerminal);

        respostas.setAdapter( ad );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelar();
            default:
                break;
        }
        return true;
    }

    private void cancelar() {
        //Enviar mensagem de cancelamento ao robo

        finishAffinity();
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
                if(s.contains("Sensor")){
                    setDistancia(mqttMessage.toString());
                }else{
                    RespostaActivity.this.atualizaTerminal("O robô disse '"+ mqttMessage.toString() +"'");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }

    private void setDistancia(String distancia) {
        this.distancia.setText("Distância detectada pelo sensor: " + distancia + "cm");
    }
}
