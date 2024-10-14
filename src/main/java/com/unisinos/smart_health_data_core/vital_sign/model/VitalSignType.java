package com.unisinos.smart_health_data_core.vital_sign.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VitalSignType {
	
	TEMPERATURE("TEMPERATURE"), HEARTRATE("HEARTRATE"), BLOODPRESSURE("BLOODPRESSURE"), 
	RESPIRATIONRATE("RESPIRATIONRATE"), SPO2("SPO2"), OXYGEN("OXYGEN"), CONSCIOUSNESS("CONSCIOUSNESS");
	
	private final String type;

	@Override
	public String toString() {
		return type;
	}
	
}
