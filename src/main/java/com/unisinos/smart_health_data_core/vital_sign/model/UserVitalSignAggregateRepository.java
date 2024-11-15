package com.unisinos.smart_health_data_core.vital_sign.model;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unisinos.smart_health_data_core.patient.model.Gender;

public interface UserVitalSignAggregateRepository extends JpaRepository<UserVitalSignAggregate, String> {
	
	String a = ";";
	
	Optional<UserVitalSignAggregate> findByPatient_id(String patientId);
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue > 120"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5"
			+ " AND vs.lastSpo2Value < 90")
	int countTotalCovid();
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " LEFT JOIN vs.patient p"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue > 120"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5"
			+ " AND vs.lastSpo2Value < 90"
			+ " AND p.gender = :gender")
	int countCovidByGender(Gender gender);
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " LEFT JOIN vs.patient p"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue > 120"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5"
			+ " AND vs.lastSpo2Value < 90"
			+ " AND p.birthDate <= :birthDateLimit")
	int countCovidElderly(Date birthDateLimit);
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue < 90"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5")
	int countTotalPneumonia();
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " LEFT JOIN vs.patient p"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue < 90"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5"
			+ " AND p.gender = :gender")
	int countPneumoniaByGender(Gender gender);
	
	@Query(value = "SELECT COUNT(vs)"
			+ " FROM UserVitalSignAggregate vs"
			+ " LEFT JOIN vs.patient p"
			+ " WHERE vs.lastHeartRateValue > 100"
			+ " AND vs.lastBloodPressureValue < 90"
			+ " AND vs.lastRespirationRateValue > 20"
			+ " AND vs.lastTemperatureValue > 37.5"
			+ " AND p.birthDate <= :birthDateLimit")
	int countPneumoniaElderly(Date birthDateLimit);
}
