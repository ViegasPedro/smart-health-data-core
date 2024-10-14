package com.unisinos.smart_health_data_core.patient.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String>{

}
