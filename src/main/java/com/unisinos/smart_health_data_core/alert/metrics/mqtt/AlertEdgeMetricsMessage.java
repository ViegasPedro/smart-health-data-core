package com.unisinos.smart_health_data_core.alert.metrics.mqtt;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertEdgeMetricsMessage {
	private String edgeId;
	private int totalAlerts;
	private Date registeredAt;
}
