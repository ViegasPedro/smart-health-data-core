package com.unisinos.smart_health_data_core.alert.metrics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.alert.metrics.model.AlertEdgeMetrics;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertEdgeMetricsConverter;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertEdgeMetricsRepository;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertMetrics;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertMetricsConverter;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertMetricsRepository;
import com.unisinos.smart_health_data_core.alert.metrics.mqtt.AlertEdgeMetricsMessage;
import com.unisinos.smart_health_data_core.alert.metrics.mqtt.AlertMetricsMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertMetricsService {
	
	private final AlertMetricsRepository alertMetricsrepository;
	private final AlertEdgeMetricsRepository alertEdgeRepository;
	private final AlertMetricsConverter alertMetricsConverter;
	private final AlertEdgeMetricsConverter alertEdgeMetricsConverter;
	
	public void processAlertMetricsMessage(AlertMetricsMessage message) {
		this.alertMetricsrepository.save(this.alertMetricsConverter.convert(message));
	}
	
	public void processAlertEdgeMetricsMessage(AlertEdgeMetricsMessage message) {
		this.alertEdgeRepository.save(this.alertEdgeMetricsConverter.convert(message));
	}
	
	public void saveMetrics(AlertMetrics metrics) {
		this.alertMetricsrepository.save(metrics);
	}
	
	public void saveAllEdgeMetrics(List<AlertEdgeMetrics> edgeMetrics) {
		this.alertEdgeRepository.saveAll(edgeMetrics);
	}

}
