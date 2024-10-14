package com.unisinos.smart_health_data_core.alert.model;

import java.util.Date;

import com.unisinos.smart_health_data_core.edge.model.Edge;

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
public class Alert {
	@Id
	private String id;
	private Date date;
	@Enumerated(EnumType.STRING)
	private AlertScoreType scoreType;
	private int newsScore;
    @ManyToOne
    @JoinColumn(name="edge_id")
	private Edge edge;
	private String sensorId;
	private String userId;	
}
