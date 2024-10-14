package com.unisinos.smart_health_data_core.patient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
	MALE("Male"), FEMALE("Female");
	
	private final String type;

	@Override
	public String toString() {
		return type;
	}
}
