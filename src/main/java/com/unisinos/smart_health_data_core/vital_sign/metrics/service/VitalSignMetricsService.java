package com.unisinos.smart_health_data_core.vital_sign.metrics.service;

import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignCountMetricsMessage;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignEdgeMetricsMessage;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignUserMetricsMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitalSignMetricsService {
	
	private final VitalSignCountMetricsRepository countMetricsRepository;
	private final VitalSignUserMetricsRepository userMetricsRepository;
	private final VitalSignEdgeMetricsRepository edgeMetricsRepository;
	private final VitalSignCountMetricsConverter countConverter;
	private final VitalSignEdgeMetricsConverter edgeConverter;
	private final VitalSignUserMetricsConverter userConverter;

	public void processCountMetricMessage(VitalSignCountMetricsMessage message) {
		countMetricsRepository.save(countConverter.convert(message));
	}
	
	public void processEdgeMetricMessage(VitalSignEdgeMetricsMessage message) {
		edgeMetricsRepository.save(edgeConverter.convert(message));
	}
	
	public void processUserMetricMessage(VitalSignUserMetricsMessage message) {
		userMetricsRepository.save(userConverter.convert(message));
	}
}
