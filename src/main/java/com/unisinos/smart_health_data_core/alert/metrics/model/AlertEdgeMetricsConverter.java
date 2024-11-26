package com.unisinos.smart_health_data_core.alert.metrics.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
	
	public List<AlertEdgeMetricsMessage> convertToMessageList(List<AlertEdgeMetrics> sourceList) {
		return sourceList.stream().map(this::convertToMessage).collect(Collectors.toList());
	}
	
	public AlertEdgeMetricsMessage convertToMessage(AlertEdgeMetrics source) {
		AlertEdgeMetricsMessage message = new AlertEdgeMetricsMessage();
		message.setEdgeId(source.getEdge().getId());
		message.setRegisteredAt(source.getRegisteredAt());
		message.setTotalAlerts(source.getTotalAlerts());
		return message;
	}
}
