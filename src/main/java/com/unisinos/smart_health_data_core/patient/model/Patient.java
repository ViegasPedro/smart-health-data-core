package com.unisinos.smart_health_data_core.patient.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient {

	public Patient(String id) {
		this.id = id;
	}
	
	@Id
	private String id;
	private Date birthDate;
	@Enumerated(EnumType.STRING)
	private Gender gender;
}
