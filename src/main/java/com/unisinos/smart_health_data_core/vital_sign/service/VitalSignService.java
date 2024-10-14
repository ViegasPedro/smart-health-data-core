package com.unisinos.smart_health_data_core.vital_sign.service;

import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.vital_sign.model.VitalSign;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitalSignService {

	private final VitalSignRepository repository;
	
	public void processMessage(VitalSign vitalSign) {
		//salva no banco
		//envia para cloud
	}
}
