package com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalSignUserMetricsMessage {
	private Date registeredAt;
	
	private int covidTotal;
	private int covidElderly;
	private int covidMale;
	private int covidFemale;
	
	private int pneumoniaTotal;
	private int pneumoniaElderly;
	private int pneumoniaMale;
	private int pneumoniaFemale;
}
