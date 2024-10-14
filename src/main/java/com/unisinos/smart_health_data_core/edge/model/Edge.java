package com.unisinos.smart_health_data_core.edge.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Edge {
	
    public Edge(String id) {
        this.id = id;
    }
	
	@Id
	private String id;
	private BigDecimal latitude;
	private BigDecimal longitude;
}
