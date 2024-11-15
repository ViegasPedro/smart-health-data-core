package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.Date;

import com.unisinos.smart_health_data_core.edge.model.Edge;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VitalSignEdgeMetrics {

	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	private VitalSignType vitalSignType;
	@ManyToOne
    @JoinColumn(name="edge_id")
	private Edge edge;
	private Date registeredAt;
	private double averageNewsScore;
}
