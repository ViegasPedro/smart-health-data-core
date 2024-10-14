package com.unisinos.smart_health_data_core.alert.metrics.model;

import java.util.Date;

import com.unisinos.smart_health_data_core.edge.model.Edge;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AlertEdgeMetrics {

	@Id
	private String id;
	@ManyToOne
    @JoinColumn(name="edge_id")
	private Edge edge;
	private int totalAlerts;
	private Date registeredAt;
}
