package com.unisinos.smart_health_data_core.vital_sign.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSign;
import com.unisinos.smart_health_data_core.vital_sign.service.VitalSignService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VitalSignSubscriber implements IMqttMessageListener {

	private final VitalSignService vitalSignService;
	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		pool.execute(new MessageHandler(message));
	}

	class MessageHandler implements Runnable {
		MqttMessage message;

		public MessageHandler(MqttMessage message) {
			this.message = message;
		}

		public void run() {
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
					.create();
			JSONObject json = new JSONObject(new String(message.getPayload()));
			VitalSign vitalSign = gson.fromJson(json.toString(), VitalSign.class);
			vitalSignService.processMessage(vitalSign);
		}
	}
}
