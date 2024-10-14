package com.unisinos.smart_health_data_core.alert.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.alert.model.Alert;
import com.unisinos.smart_health_data_core.alert.model.AlertRepository;
import com.unisinos.smart_health_data_core.alert.model.AlertScoreType;
import com.unisinos.smart_health_data_core.alert.mqtt.AlertMessage;
import com.unisinos.smart_health_data_core.alert.mqtt.AlertVitalSign;
import com.unisinos.smart_health_data_core.edge.model.Edge;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlertService {
	
	@Autowired
	private AlertRepository repository;
	
	public List<Alert> getAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "date"));
	}
	
	public void deleteAllProcessed(Date lastProcessedAlertDate) {
		this.repository.deleteByDateLessThanEqual(lastProcessedAlertDate);
	}
	
	public void processMessage(AlertMessage message) {
		this.saveAlert(message);
		this.sendAlert(message);
	}
	
	private void saveAlert(AlertMessage message) {
		Alert alert = new Alert();
		alert.setId(UUID.randomUUID().toString());
		alert.setDate(message.getDate());
		alert.setScoreType(message.getVitalSigns().size() == 1 ? AlertScoreType.SINGLE_VITAL_SIGN : AlertScoreType.MULTI_VITAL_SIGN);
		alert.setEdge(new Edge(message.getEdgeId()));
		alert.setNewsScore(sumNewsScore(message.getVitalSigns()));
		alert.setSensorId(message.getSensorId());
		alert.setUserId(message.getUserId());
		repository.save(alert);
	}
	
	private int sumNewsScore(List<AlertVitalSign> alertVitalSignList) {
        return alertVitalSignList.stream()
                .mapToInt(AlertVitalSign::getNewsScore)
                .sum();
    }
	
	private void sendAlert(AlertMessage message) {
		//here the alert message can be send to a hospital api, hospital MQTT broker, edge device ...
		log.info("Alert sent to hospital");
	}

}
