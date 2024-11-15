package com.unisinos.smart_health_data_core.alert.metrics.mqtt;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertMetricsMessage {
	private int total;
	private int singleVitalSign;
	private int multiVitalSign;
	private Date registeredAt;
	private int users;
}
