package com.unisinos.smart_health_data_core.vital_sign.metrics.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.commons.SmartHealthDataProperties;
import com.unisinos.smart_health_data_core.commons.mqtt.MqttPublisher;
import com.unisinos.smart_health_data_core.commons.mqtt.MqttTopicUtils;
import com.unisinos.smart_health_data_core.patient.model.Gender;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetrics;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetrics;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetrics;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetricsConverter;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetricsRepository;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignCountMetricsMessage;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignEdgeMetricsMessage;
import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignUserMetricsMessage;
import com.unisinos.smart_health_data_core.vital_sign.model.UserVitalSignAggregateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VitalSignMetricsService {
	
	private final VitalSignCountMetricsRepository countMetricsRepository;
	private final VitalSignUserMetricsRepository userMetricsRepository;
	private final VitalSignEdgeMetricsRepository edgeMetricsRepository;
	private final UserVitalSignAggregateRepository userAggregateRepository;
	private final VitalSignCountMetricsConverter countConverter;
	private final VitalSignEdgeMetricsConverter edgeConverter;
	private final VitalSignUserMetricsConverter userConverter;
	private final MqttPublisher publisher;
	private final SmartHealthDataProperties properties;

	public void processCountMetricMessage(VitalSignCountMetricsMessage message) {
		countMetricsRepository.save(countConverter.convert(message));
	}
	
	public void processEdgeMetricMessage(VitalSignEdgeMetricsMessage message) {
		edgeMetricsRepository.save(edgeConverter.convert(message));
	}
	
	public void processUserMetricMessage(VitalSignUserMetricsMessage message) {
		userMetricsRepository.save(userConverter.convert(message));
	}
	
	public void saveAllcountMetrics(List<VitalSignCountMetrics> countMetrics) {
		countMetricsRepository.saveAll(countMetrics);
	}
	
	public void saveAllEdgeMetrics(List<VitalSignEdgeMetrics> edgeMetrics) {
		edgeMetricsRepository.saveAll(edgeMetrics);
	}
	
	public void saveUserMetrics(VitalSignUserMetrics userMetrics) {
		userMetricsRepository.save(userMetrics);
	}
	
	public int countTotalCovid() {
		return userAggregateRepository.countTotalCovid();
	}
	
	public int countCovidMale() {
		return userAggregateRepository.countCovidByGender(Gender.MALE);
	}
	
	public int countCovidFemale() {
		return userAggregateRepository.countCovidByGender(Gender.FEMALE);
	}
	
	public int countCovidElderly() {
		LocalDate localDate = LocalDate.now().minusYears(65);
		Date elderlyDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return userAggregateRepository.countCovidElderly(elderlyDate);
	}
	
	public int countTotalPneumonia() {
		return userAggregateRepository.countTotalPneumonia();
	}
	
	public int countPneumoniaMale() {
		return userAggregateRepository.countPneumoniaByGender(Gender.MALE);
	}
	
	public int countPneumoniaFemale() {
		return userAggregateRepository.countPneumoniaByGender(Gender.FEMALE);
	}
	
	public int countPneumoniaElderly() {
		LocalDate localDate = LocalDate.now().minusYears(65);
		Date elderlyDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return userAggregateRepository.countPneumoniaElderly(elderlyDate);
	}
	
	public void sendResultToParent(List<VitalSignCountMetrics> countMetrics, List<VitalSignEdgeMetrics> edgeMetrics
			, VitalSignUserMetrics userMetrics) {
		if (!properties.getParent().getIsCloud()) {
			try {
				if (countMetrics != null && !countMetrics.isEmpty()) {
					publisher.publishToParent(MqttTopicUtils.VITAL_SIGN_COUNT_METRICS_TOPIC, countConverter.convertToMessageList(countMetrics));
				}
				if (edgeMetrics != null && !edgeMetrics.isEmpty()) {
					publisher.publishToParent(MqttTopicUtils.VITAL_SIGN_EDGE_METRICS_TOPIC, edgeConverter.convertToMessageList(edgeMetrics));
				}
				if (userMetrics != null) {
					publisher.publishToParent(MqttTopicUtils.VITAL_SIGN_USER_METRICS_TOPIC, userConverter.convertToMessage(userMetrics));
				}
			} catch (Exception e) {
				log.error("Error sending vital sign result message to parent. {}", e);
			}
		}
	}
}
