package com.unisinos.smart_health_data_core.commons.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.alert.metrics.mqtt.AlertMetricsSubscriber;
import com.unisinos.smart_health_data_core.commons.SmartHealthDataProperties;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignMetricsSubscriber;
import com.unisinos.smart_health_data_core.vital_sign.mqtt.VitalSignSubscriber;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MqttClient {

	private final SmartHealthDataProperties properties;
	private final AlertMetricsSubscriber alertMetricsSubscriber;
	private final VitalSignSubscriber vitalSignSubscriber;
	private final VitalSignMetricsSubscriber vitalSignMetricsSubscriber;

	private MqttAsyncClient client = null;
	private MqttAsyncClient parentClient = null;
	
	public MqttAsyncClient getClient() {
		if (client == null)
			connect();
		return client;
	}
	
	public MqttAsyncClient getParentClient() {
		if (parentClient == null)
			connectToParent();
		return parentClient;
	}

	private void connect() {        
    	MqttConnectOptions options = new MqttConnectOptions();
    	options.setUserName(this.properties.getMqtt().getUser());
        options.setPassword(this.properties.getMqtt().getPassword().toCharArray());
        options.setCleanSession(true);
        options.setKeepAliveInterval(30);
        
        try {
        	MqttAsyncClient client = new MqttAsyncClient(this.properties.getMqtt().getServerUrl(), this.properties.getMqtt().getClientId());
	        client.connect(options, null, new IMqttActionListener() {
	            @Override
	            public void onSuccess(IMqttToken asyncActionToken) {
	                try {
	                    String[] topics = {MqttTopicUtils.ALERT_METRICS_TOPIC, MqttTopicUtils.ALERT_EDGE_METRICS_TOPIC, 
	                    		MqttTopicUtils.VITAL_SIGN_TOPIC,  MqttTopicUtils.VITAL_SIGN_PROCESSED_TOPIC,
	                    		MqttTopicUtils.VITAL_SIGN_COUNT_METRICS_TOPIC, MqttTopicUtils.VITAL_SIGN_EDGE_METRICS_TOPIC, MqttTopicUtils.VITAL_SIGN_USER_METRICS_TOPIC};
	                    int[] qos = {1, 1, 1, 1, 1, 1, 1};
	                    IMqttMessageListener[] listeners = {alertMetricsSubscriber, alertMetricsSubscriber, 
	                    		vitalSignSubscriber, vitalSignSubscriber,
	                    		vitalSignMetricsSubscriber, vitalSignMetricsSubscriber, vitalSignMetricsSubscriber};
	
	                    client.subscribe(topics, qos, listeners);
	
	                } catch (MqttException e) {
	                    e.printStackTrace();
	                }
	            }
	
	            @Override
	            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
	                System.err.println("Error connecting to MQTT broker: " + exception.getMessage());
	            }
	        });
	        this.client = client;
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	private void connectToParent() {        
    	MqttConnectOptions options = new MqttConnectOptions();
    	options.setUserName(this.properties.getParent().getMqtt().getUser());
        options.setPassword(this.properties.getParent().getMqtt().getPassword().toCharArray());
        options.setCleanSession(true);
        options.setKeepAliveInterval(30);
        
        try {
        	MqttAsyncClient client = new MqttAsyncClient(this.properties.getParent().getMqtt().getServerUrl(), this.properties.getParent().getMqtt().getClientId());
	        client.connect(options);
	        this.client = client;
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}

}