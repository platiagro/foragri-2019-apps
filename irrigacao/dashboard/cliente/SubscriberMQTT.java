package cliente;

import java.net.URISyntaxException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscriberMQTT implements MqttCallback {

    private final int qosMQTT = 2;
    private String topicoCompletoMQTT = null;
    private MqttClient clienteMQTT;

    private String msgRecebida = null;


	public SubscriberMQTT( String identificador ) throws MqttException {
		String topicoCompleto = null;
        String host = "tcp://10.50.11.147:1883";
        String clientId = "admin:" + identificador;

        topicoCompleto = "/admin/";
        topicoCompleto += identificador;
        topicoCompletoMQTT = topicoCompleto + "/config";
        
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);

        this.clienteMQTT = new MqttClient(host, clientId, new MemoryPersistence());
        this.clienteMQTT.setCallback(this);
        this.clienteMQTT.connect(conOpt);

        try {
			Thread.sleep(3000);
		} 
		catch (Exception e) {
			System.out.println("Falha ao tentar suspender processo principal");
		}
        
        System.out.println("Deve ter conectado");
        this.clienteMQTT.subscribe(this.topicoCompletoMQTT, qosMQTT);
    }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qosMQTT);
        this.clienteMQTT.publish(this.topicoCompletoMQTT, message); // Blocking publish
    }

    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
    }


    public void messageArrived(String msg, MqttMessage message) throws MqttException {
    	String urlModelo = null;
    	int posInic = 0;
    	
    	System.out.println(String.format("[%s] %s", msg, new String(message.getPayload())));
        
        //Tratamento da mensagem para obter e gravar somente a URL
        
        
       //[/admin/72269c/config] 
       //{"url":"http://10.50.11.180:31380/seldon/kubeflow/falha-automl-ee7e1094-ffbb-44fd-881b-2f3fa539780f/api/v0.1/predictions"}
    	msg = new String(message.getPayload());
    	posInic = msg.indexOf( "http");
    	
    	if( posInic >= 0 ) {
    		urlModelo = msg.substring( posInic ); //Pega ate o fim
    		urlModelo = urlModelo.replace( "{", "");
    		urlModelo = urlModelo.replace( "}", "");
    		urlModelo = urlModelo.replace( "\"", "");
    		
    		this.setMsgRecebida( urlModelo );
    	}
        
    }
    
    //Gets & Sets
    public String getMsgRecebida() {
		return msgRecebida;
	}
	public void setMsgRecebida(String msgRecebida) {
		this.msgRecebida = msgRecebida;
	}


    public static void main(String[] args) throws MqttException, URISyntaxException {
        SubscriberMQTT s = new SubscriberMQTT( "72269c" );
        
        while( true ) {
        	System.out.println("esperando URL do modelo...");

        	try {
				Thread.sleep(3000);
			} 
			catch (Exception e) {
				System.out.println("Falha ao tentar suspender processo principal");
			}        
        }
    }
}
