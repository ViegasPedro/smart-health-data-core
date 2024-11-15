package com.unisinos.smart_health_data_core.vital_sign.model;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VitalSignRepository extends JpaRepository<VitalSign, String>{
	void deleteByDateLessThanEqual(Date date);
}
