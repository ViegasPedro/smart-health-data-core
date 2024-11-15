package com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisinos.smart_health_data_core.commons.mqtt.MqttTopicUtils;
import com.unisinos.smart_health_data_core.vital_sign.metrics.service.VitalSignMetricsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VitalSignMetricsSubscriber implements IMqttMessageListener {

	private final VitalSignMetricsService vitalSignMetricsService;
	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		pool.execute(new MessageHandler(topic, message));
	}

	class MessageHandler implements Runnable {
		MqttMessage message;
		String topic;

		public MessageHandler(String topic, MqttMessage message) {
			this.message = message;
			this.topic = topic;
		}

		public void run() {
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
					.create();
			JSONObject json = new JSONObject(new String(message.getPayload()));
			
			if (topic.equalsIgnoreCase(MqttTopicUtils.VITAL_SIGN_COUNT_METRICS_TOPIC)) {
				VitalSignCountMetricsMessage message = gson.fromJson(json.toString(), VitalSignCountMetricsMessage.class);
				vitalSignMetricsService.processCountMetricMessage(message);
				
			} else if (topic.equalsIgnoreCase(MqttTopicUtils.VITAL_SIGN_EDGE_METRICS_TOPIC)) {
				VitalSignEdgeMetricsMessage message = gson.fromJson(json.toString(), VitalSignEdgeMetricsMessage.class);
				vitalSignMetricsService.processEdgeMetricMessage(message);
				
			} else if (topic.equalsIgnoreCase(MqttTopicUtils.VITAL_SIGN_USER_METRICS_TOPIC)) {
				VitalSignUserMetricsMessage message = gson.fromJson(json.toString(), VitalSignUserMetricsMessage.class);
				vitalSignMetricsService.processUserMetricMessage(message);
				
			} else {
				log.warn("Ignoring invalid message with topic: {}", topic);
			}
		}
	}
}
