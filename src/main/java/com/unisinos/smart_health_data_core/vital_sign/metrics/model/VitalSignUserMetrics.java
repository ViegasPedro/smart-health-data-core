package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VitalSignUserMetrics {

	@Id
	private String id;
	private Date registeredAt;
	
	private int covidTotal;
	private int covidElderly;
	private int covidMale;
	private int covidFemale;
	
	private int pneumoniaTotal;
	private int pneumoniaElderly;
	private int pneumoniaMale;
	private int pneumoniaFemale;
}
