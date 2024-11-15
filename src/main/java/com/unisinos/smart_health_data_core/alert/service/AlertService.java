package com.unisinos.smart_health_data_core.alert.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.alert.model.Alert;
import com.unisinos.smart_health_data_core.alert.model.AlertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertService {
	
	private final AlertRepository repository;
	
	public List<Alert> getAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "date"));
	}
	
	public void deleteAllProcessed(Date lastProcessedAlertDate) {
		this.repository.deleteByDateLessThanEqual(lastProcessedAlertDate);
	}

}
