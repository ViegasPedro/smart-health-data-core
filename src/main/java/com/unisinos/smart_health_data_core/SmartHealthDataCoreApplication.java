package com.unisinos.smart_health_data_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

import com.unisinos.smart_health_data_core.commons.SmartHealthDataProperties;
import com.unisinos.smart_health_data_core.commons.mqtt.MqttClient;

@SpringBootApplication
@EnableConfigurationProperties(value = { SmartHealthDataProperties.class })
public class SmartHealthDataCoreApplication {

	@Autowired
	private MqttClient mqttClient;
	
	public static void main(String[] args) {
		SpringApplication.run(SmartHealthDataCoreApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void startMqttClient() {
	    this.mqttClient.getClient();
	}

}
