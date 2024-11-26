package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignCountMetricsMessage;

@Component
public class VitalSignCountMetricsConverter implements Converter<VitalSignCountMetricsMessage, VitalSignCountMetrics> {

	@Override
	public VitalSignCountMetrics convert(VitalSignCountMetricsMessage source) {
		VitalSignCountMetrics metrics = new VitalSignCountMetrics();
		metrics.setId(UUID.randomUUID().toString());
		metrics.setCountNews0(source.getCountNews0());
		metrics.setCountNews1(source.getCountNews1());
		metrics.setCountNews2(source.getCountNews2());
		metrics.setCountNews3(source.getCountNews3());
		metrics.setMean(source.getMean());
		metrics.setRegisteredAt(source.getRegisteredAt());
		metrics.setTotal(source.getTotal());
		metrics.setVitalSignType(source.getVitalSignType());
		return metrics;
	}
	
	public List<VitalSignCountMetricsMessage> convertToMessageList(List<VitalSignCountMetrics> sourceList) {
		return sourceList.stream().map(this::convertToMessage).collect(Collectors.toList());
	}

	public VitalSignCountMetricsMessage convertToMessage(VitalSignCountMetrics source) {
		VitalSignCountMetricsMessage message = new VitalSignCountMetricsMessage();
		message.setCountNews0(source.getCountNews0());
		message.setCountNews1(source.getCountNews1());
		message.setCountNews2(source.getCountNews2());
		message.setCountNews3(source.getCountNews3());
		message.setMean(source.getMean());
		message.setRegisteredAt(source.getRegisteredAt());
		message.setTotal(source.getTotal());
		message.setVitalSignType(source.getVitalSignType());
		return message;
	}
}
