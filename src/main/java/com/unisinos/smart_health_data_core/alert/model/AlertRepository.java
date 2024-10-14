package com.unisinos.smart_health_data_core.alert.model;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, String>{
    void deleteByDateLessThanEqual(Date date);
}
