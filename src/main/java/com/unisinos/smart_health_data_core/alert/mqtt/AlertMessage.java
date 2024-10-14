package com.unisinos.smart_health_data_core.alert.mqtt;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertMessage {

	private String userId;
	private String sensorId;
	private String edgeId;
	private Date date;
	private List<AlertVitalSign> vitalSigns; 	
}