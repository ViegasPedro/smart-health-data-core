package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.UUID;

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
}
