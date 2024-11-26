package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.edge.model.Edge;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignEdgeMetricsMessage;

@Component
public class VitalSignEdgeMetricsConverter implements Converter<VitalSignEdgeMetricsMessage, VitalSignEdgeMetrics> {

	@Override
	public VitalSignEdgeMetrics convert(VitalSignEdgeMetricsMessage source) {
		VitalSignEdgeMetrics metrics = new VitalSignEdgeMetrics();
		metrics.setId(UUID.randomUUID().toString());
		metrics.setAverageNewsScore(source.getAverageNewsScore());
		metrics.setEdge(new Edge(source.getEdgeId()));
		metrics.setRegisteredAt(source.getRegisteredAt());
		metrics.setVitalSignType(source.getVitalSignType());
		return metrics;
	}
	
	public List<VitalSignEdgeMetricsMessage> convertToMessageList(List<VitalSignEdgeMetrics> sourceList) {
		return sourceList.stream().map(this::convertToMessage).collect(Collectors.toList());
	}
	
	public VitalSignEdgeMetricsMessage convertToMessage(VitalSignEdgeMetrics source) {
		VitalSignEdgeMetricsMessage message = new VitalSignEdgeMetricsMessage();
		message.setAverageNewsScore(source.getAverageNewsScore());
		message.setEdgeId(source.getEdge().getId());
		message.setRegisteredAt(source.getRegisteredAt());
		message.setVitalSignType(source.getVitalSignType());
		return message;
	}
}
