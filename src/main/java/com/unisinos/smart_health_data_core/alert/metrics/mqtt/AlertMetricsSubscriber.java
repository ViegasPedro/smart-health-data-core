package com.unisinos.smart_health_data_core.alert.metrics.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisinos.smart_health_data_core.alert.metrics.service.AlertMetricsService;
import com.unisinos.smart_health_data_core.commons.mqtt.MqttTopicUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertMetricsSubscriber implements IMqttMessageListener {

	private final AlertMetricsService alertMetricsService;
	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		pool.execute(new MessageHandler(topic, message));
	}

	class MessageHandler implements Runnable {
		MqttMessage message;
		String topic;

		public MessageHandler(String topic, MqttMessage message) {
			this.topic = topic;
			this.message = message;
		}

		public void run() {
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
					.create();
			JSONObject json = new JSONObject(new String(message.getPayload()));
			
			if (topic.equalsIgnoreCase(MqttTopicUtils.ALERT_METRICS_TOPIC)) {
				AlertMetricsMessage message = gson.fromJson(json.toString(), AlertMetricsMessage.class);
				alertMetricsService.processAlertMetricsMessage(message);
				
			} else if (topic.equalsIgnoreCase(MqttTopicUtils.ALERT_EDGE_METRICS_TOPIC)) {
				AlertEdgeMetricsMessage message = gson.fromJson(json.toString(), AlertEdgeMetricsMessage.class);
				alertMetricsService.processAlertEdgeMetricsMessage(message);
			} else {
				log.warn("Ignoring invalid message with topic: {}", topic);
			}
		}
	}

}
