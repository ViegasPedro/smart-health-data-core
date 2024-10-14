package com.unisinos.smart_health_data_core.alert.metrics.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AlertMetrics {

	@Id
	private String id;
	private int total;
	private int singleVitalSign;
	private int multiVitalSign;
	private Date registeredAt;
	private int users;
}
