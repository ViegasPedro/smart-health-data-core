package com.unisinos.smart_health_data_core.vital_sign.model;

import java.util.Date;

import com.unisinos.smart_health_data_core.patient.model.Patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserVitalSignAggregate {
	
	@Id
	private String id;
	@OneToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	private Double lastTemperatureValue;
	private Double lastHeartRateValue;
	private Double lastBloodPressureValue;
	private Double lastRespirationRateValue;
	@Column(name = "last_spo2_value")
	private Double lastSpo2Value;
	private String lastOxygenValue;
	private String lastConsciousnessValue;
	private Date lastUpdate;
}