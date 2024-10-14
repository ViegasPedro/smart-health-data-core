package com.unisinos.smart_health_data_core.vital_sign.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VitalSign {

	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	private VitalSignType type;
	private String value;
	private Date date;
	private String edgeId;
	private String sensorId;
	private String userId;
	private Integer newsScore;
}