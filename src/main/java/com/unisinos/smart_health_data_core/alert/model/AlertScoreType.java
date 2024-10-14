package com.unisinos.smart_health_data_core.alert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlertScoreType {
	
	SINGLE_VITAL_SIGN("SINGLE_VITAL_SIGN"), MULTI_VITAL_SIGN("MULTI_VITAL_SIGN");
	
	private final String type;

	@Override
	public String toString() {
		return type;
	}
}
