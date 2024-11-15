package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.UUID;

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

}
