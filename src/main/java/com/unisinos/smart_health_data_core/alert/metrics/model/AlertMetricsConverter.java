package com.unisinos.smart_health_data_core.alert.metrics.model;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.alert.metrics.mqtt.AlertMetricsMessage;

@Component
public class AlertMetricsConverter implements Converter<AlertMetricsMessage, AlertMetrics> {

	@Override
	public AlertMetrics convert(AlertMetricsMessage source) {
		AlertMetrics metrics = new AlertMetrics();
		metrics.setId(UUID.randomUUID().toString());
		metrics.setMultiVitalSign(source.getMultiVitalSign());
		metrics.setRegisteredAt(source.getRegisteredAt());
		metrics.setSingleVitalSign(source.getSingleVitalSign());
		metrics.setTotal(source.getTotal());
		metrics.setUsers(source.getUsers());
		return metrics;
	}

}
