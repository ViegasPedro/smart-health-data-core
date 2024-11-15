package com.unisinos.smart_health_data_core.vital_sign.metrics.model;

import java.util.Date;

import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VitalSignCountMetrics {
	
	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	private VitalSignType vitalSignType;
	private long total;
	private double mean;
    @Column(name = "count_news_0")
    private long countNews0;
    @Column(name = "count_news_1")
    private long countNews1;
    @Column(name = "count_news_2")
    private long countNews2;
    @Column(name = "count_news_3")
    private long countNews3;
    private Date registeredAt;
}
