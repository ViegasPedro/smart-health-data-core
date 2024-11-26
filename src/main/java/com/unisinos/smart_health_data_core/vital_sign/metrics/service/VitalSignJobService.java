package com.unisinos.smart_health_data_core.vital_sign.metrics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unisinos.smart_health_data_core.edge.model.Edge;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignCountMetrics;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignEdgeMetrics;
import com.unisinos.smart_health_data_core.vital_sign.metrics.model.VitalSignUserMetrics;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSign;
import com.unisinos.smart_health_data_core.vital_sign.model.VitalSignType;
import com.unisinos.smart_health_data_core.vital_sign.service.VitalSignService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VitalSignJobService {

	private final VitalSignService vitalSignService;
	private final VitalSignMetricsService vitalSignMetricsService;
			
	@Scheduled(cron = "*/5 * * * * *")
	@Transactional
	public void createMetrics() {
		List<VitalSign> vitalSigns = vitalSignService.getAll();
		List<VitalSignCountMetrics> countMetrics = null;
		List<VitalSignEdgeMetrics> edgeMetrics = null;
		
		if (!vitalSigns.isEmpty()) {
			countMetrics = generateVitalSignCountMetrics(vitalSigns);
			this.vitalSignMetricsService.saveAllcountMetrics(countMetrics);
			
			edgeMetrics = generateVitalSignEdgeMetrics(vitalSigns);
			this.vitalSignMetricsService.saveAllEdgeMetrics(edgeMetrics);
			
			Date lastSignDate = vitalSigns.get(vitalSigns.size() - 1).getDate();
			this.vitalSignService.deleteAllProcessed(lastSignDate);
		}
		
		VitalSignUserMetrics userMetrics = generateVitalSignUserMetrics();
		this.vitalSignMetricsService.saveUserMetrics(userMetrics);
		this.vitalSignMetricsService.sendResultToParent(countMetrics, edgeMetrics, userMetrics);
	}
	
	private List<VitalSignCountMetrics> generateVitalSignCountMetrics(List<VitalSign> vitalSigns) {
	    return vitalSigns.stream()
	        .collect(Collectors.groupingBy(VitalSign::getType))
	        .entrySet().stream()
	        .map(entry -> {
	            VitalSignType type = entry.getKey();
	            List<VitalSign> signs = entry.getValue();

	            int total = signs.size();
	            double averageNews = signs.stream().mapToInt(VitalSign::getNewsScore).average().orElse(0);
	            long newsScore0 = signs.stream().filter(v -> v.getNewsScore() == 0).count();
	            long newsScore1 = signs.stream().filter(v -> v.getNewsScore() == 1).count();
	            long newsScore2 = signs.stream().filter(v -> v.getNewsScore() == 2).count();
	            long newsScore3 = signs.stream().filter(v -> v.getNewsScore() == 3).count();

	            return new VitalSignCountMetrics(UUID.randomUUID().toString(), type, total, averageNews, newsScore0, newsScore1, newsScore2, newsScore3, new Date());
	        })
	        .collect(Collectors.toList());
	}
	
	private VitalSignUserMetrics generateVitalSignUserMetrics() {
		VitalSignUserMetrics userMetrics = new VitalSignUserMetrics();
		userMetrics.setId(UUID.randomUUID().toString());
		userMetrics.setRegisteredAt(new Date());
				
		userMetrics.setCovidTotal(vitalSignMetricsService.countTotalCovid());
		userMetrics.setCovidFemale(vitalSignMetricsService.countCovidFemale());
		userMetrics.setCovidMale(vitalSignMetricsService.countCovidMale());
		userMetrics.setCovidElderly(vitalSignMetricsService.countCovidElderly());
		
		userMetrics.setPneumoniaTotal(vitalSignMetricsService.countTotalPneumonia());
		userMetrics.setPneumoniaFemale(vitalSignMetricsService.countPneumoniaFemale());
		userMetrics.setPneumoniaMale(vitalSignMetricsService.countPneumoniaMale());
		userMetrics.setPneumoniaElderly(vitalSignMetricsService.countPneumoniaElderly());
		
		return userMetrics;
	}
	
	private List<VitalSignEdgeMetrics> generateVitalSignEdgeMetrics(List<VitalSign> vitalSigns) {
        Map<String, Map<VitalSignType, List<VitalSign>>> groupedVitalSigns = vitalSigns.stream()
                .collect(Collectors.groupingBy(VitalSign::getEdgeId,
                        Collectors.groupingBy(VitalSign::getType)));

        List<VitalSignEdgeMetrics> metricsList = new ArrayList<>();

        groupedVitalSigns.forEach((edgeId, typeMap) -> {
            typeMap.forEach((vitalSignType, vitalSignsList) -> {
                double averageNewsScore = vitalSignsList.stream()
                        .mapToInt(VitalSign::getNewsScore)
                        .average()
                        .orElse(0.0);

                VitalSignEdgeMetrics metrics = new VitalSignEdgeMetrics();
                metrics.setId(UUID.randomUUID().toString());
                metrics.setEdge(new Edge(edgeId));
                metrics.setVitalSignType(vitalSignType);
                metrics.setRegisteredAt(new Date());
                metrics.setAverageNewsScore(averageNewsScore);

                metricsList.add(metrics);
            });
        });

        return metricsList;
    }
}
