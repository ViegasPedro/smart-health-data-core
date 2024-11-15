package com.unisinos.smart_health_data_core.alert.metrics.model;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.alert.metrics.mqtt.AlertEdgeMetricsMessage;
import com.unisinos.smart_health_data_core.edge.model.Edge;

@Component
public class AlertEdgeMetricsConverter implements Converter<AlertEdgeMetricsMessage, AlertEdgeMetrics> {

	@Override
	public AlertEdgeMetrics convert(AlertEdgeMetricsMessage source) {
		AlertEdgeMetrics metrics = new AlertEdgeMetrics();
		metrics.setId(UUID.randomUUID().toString());
		metrics.setEdge(new Edge(source.getEdgeId()));
		metrics.setRegisteredAt(source.getRegisteredAt());
		metrics.setTotalAlerts(source.getTotalAlerts());
		return metrics;
	}
}
