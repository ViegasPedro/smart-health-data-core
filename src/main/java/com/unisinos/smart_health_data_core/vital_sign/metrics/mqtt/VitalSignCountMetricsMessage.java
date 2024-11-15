package com.unisinos.smart_health_data_core.vital_sign.metrics.mqtt;

import java.util.Date;

import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalSignCountMetricsMessage {
	
	private VitalSignType vitalSignType;
	private long total;
	private double mean;
    private long countNews0;
    private long countNews1;
    private long countNews2;
    private long countNews3;
    private Date registeredAt;
}
