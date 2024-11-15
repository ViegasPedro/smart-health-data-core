package com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt;

import java.util.Date;

import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalSignEdgeMetricsMessage {
	private VitalSignType vitalSignType;
	private String edgeId;
	private Date registeredAt;
	private double averageNewsScore;
}
