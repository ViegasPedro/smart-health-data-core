package com.unisinos.smart_health_data_core.vital_sign.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.patient.model.Patient;
import com.unisinos.smart_health_data_core.vital_sign.model.UserVitalSignAggregate;
import com.unisinos.smart_health_data_core.vital_sign.model.UserVitalSignAggregateRepository;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSign;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VitalSignService {

	private final VitalSignRepository repository;
	private final UserVitalSignAggregateRepository aggregateRepository;
	
	public void processMessage(VitalSign vitalSign) {
		this.saveVitalSign(vitalSign);
		this.updateUserVitalSignAggregate(vitalSign);
		this.sendToNextLayer(vitalSign);
	}
	
	public void sendToNextLayer(VitalSign vitalSign) {
		//here vital sign can be sent to the next layer of fog or the cloud
		log.info("Vital Sign sent to next layer of fog or cloud");
	}
		
	public List<VitalSign> getAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "date"));
	}
	
	public void deleteAllProcessed(Date lastProcessedSignDate) {
		this.repository.deleteByDateLessThanEqual(lastProcessedSignDate);
	}
	
	private void saveVitalSign(VitalSign vitalSign) {
		vitalSign.setId(UUID.randomUUID().toString());
        repository.save(vitalSign);
	}
	
	private void updateUserVitalSignAggregate(VitalSign vitalSign) {
		Optional<UserVitalSignAggregate> aggregateOptional = aggregateRepository.findByPatient_id(vitalSign.getUserId());
		UserVitalSignAggregate aggregate = new UserVitalSignAggregate();
		if (aggregateOptional.isPresent()) {
			aggregate = aggregateOptional.get();
		} else {
			aggregate.setId(UUID.randomUUID().toString());
			aggregate.setPatient(new Patient(vitalSign.getUserId()));
		}
		aggregateVitalSign(vitalSign, aggregate);
	    aggregate.setLastUpdate(new Date());
	    aggregateRepository.saveAndFlush(aggregate);
	}
	
	private void aggregateVitalSign(VitalSign vitalSign, UserVitalSignAggregate aggregate) {
	    switch (vitalSign.getType()) {
	        case TEMPERATURE:
	            aggregate.setLastTemperatureValue(Double.valueOf(vitalSign.getValue()));
	            break;
	        case HEARTRATE:
	            aggregate.setLastHeartRateValue(Double.valueOf(vitalSign.getValue()));
	            break;
	        case BLOODPRESSURE:
	            aggregate.setLastBloodPressureValue(Double.valueOf(vitalSign.getValue()));
	            break;
	        case RESPIRATIONRATE:
	            aggregate.setLastRespirationRateValue(Double.valueOf(vitalSign.getValue()));
	            break;
	        case SPO2:
	            aggregate.setLastSpo2Value(Double.valueOf(vitalSign.getValue()));
	            break;
	        case OXYGEN:
	            aggregate.setLastOxygenValue(vitalSign.getValue());
	            break;
	        case CONSCIOUSNESS:
	            aggregate.setLastConsciousnessValue(vitalSign.getValue());
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid vital sign type: " + vitalSign.getType());
	    }
	}
	
}
