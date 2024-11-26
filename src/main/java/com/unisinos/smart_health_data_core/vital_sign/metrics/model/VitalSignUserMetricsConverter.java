package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt.VitalSignUserMetricsMessage;

@Component
public class VitalSignUserMetricsConverter implements Converter<VitalSignUserMetricsMessage, VitalSignUserMetrics> {

	@Override
	public VitalSignUserMetrics convert(VitalSignUserMetricsMessage source) {
		VitalSignUserMetrics metrics = new VitalSignUserMetrics();
		metrics.setId(UUID.randomUUID().toString());
		metrics.setCovidElderly(source.getCovidElderly());
		metrics.setCovidFemale(source.getCovidFemale());
		metrics.setCovidMale(source.getCovidMale());
		metrics.setCovidTotal(source.getCovidTotal());
		metrics.setPneumoniaElderly(source.getPneumoniaElderly());
		metrics.setPneumoniaFemale(source.getPneumoniaFemale());
		metrics.setPneumoniaMale(source.getPneumoniaMale());
		metrics.setPneumoniaTotal(source.getPneumoniaTotal());
		metrics.setRegisteredAt(source.getRegisteredAt());
		return metrics;
	}
	
	public VitalSignUserMetricsMessage convertToMessage(VitalSignUserMetrics source) {
		VitalSignUserMetricsMessage message = new VitalSignUserMetricsMessage();
		message.setCovidElderly(source.getCovidElderly());
		message.setCovidFemale(source.getCovidFemale());
		message.setCovidMale(source.getCovidMale());
		message.setCovidTotal(source.getCovidTotal());
		message.setPneumoniaElderly(source.getPneumoniaElderly());
		message.setPneumoniaFemale(source.getPneumoniaFemale());
		message.setPneumoniaMale(source.getPneumoniaMale());
		message.setPneumoniaTotal(source.getPneumoniaTotal());
		message.setRegisteredAt(source.getRegisteredAt());
		return message;
	}

}
