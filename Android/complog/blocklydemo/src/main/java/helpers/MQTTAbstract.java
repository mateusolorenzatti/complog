package helpers;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.blockly.activity.AberturaActivity;

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

public abstract class MQTTAbstract extends AppCompatActivity {
    protected MqttAndroidClient cliente;

    protected int qos = 0;
    protected String topico_publicador = "CompLog/Codigo";
    protected String topico_inscrito = "CompLog/Resposta";
    protected String topico_sensor = "CompLog/Sensor";

    protected boolean plataforma_conectada = false;
    
    public void conectar() {
        try {
            String broker = "tcp://10.200.3.83:1883";
            String usuario = "ahmlelou";
            String senha = "IbZDm2_k1MQc";

            this.cliente = new MqttAndroidClient(this.getApplicationContext(), broker, MqttClient.generateClientId());

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            //connOpts.setUserName(usuario);
            //connOpts.setPassword(senha.toCharArray());

            IMqttToken token = cliente.connect(connOpts);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Toast.makeText(mContext, "Conectei", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MQTTAbstract.this.getApplicationContext(), "Falha na Conexão", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (MqttException me) {
            Toast.makeText(this.getApplicationContext(), "Falha na Conexão", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "Falha na Conexão", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviaChecagem() throws MqttException, UnsupportedEncodingException {
        this.publicar("conectado");
    }

    public void inscrever() throws MqttException {
        this.cliente.subscribe(this.topico_inscrito, qos);

        this.cliente.subscribe(this.topico_sensor, qos);
    }

    public void publicar(String msg) throws MqttException, UnsupportedEncodingException {
        byte[] encodedPayload;
        encodedPayload = msg.getBytes("UTF-8");

        MqttMessage message = new MqttMessage(encodedPayload);

        message.setRetained(true);
        message.setQos(qos);

        this.cliente.publish(topico_publicador, message);
    }

    public boolean isPlataforma_conectada(){
        return this.plataforma_conectada;
    }

    public boolean isConexao_estabelecida(){
        return this.cliente.isConnected();
    }

    //Tratamento especial em cada Activity que utiliza
    protected abstract void setCallback ();
}
