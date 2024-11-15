package com.unisinos.smart_health_data_core.alert.metrics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.alert.metrics.model.AlertEdgeMetrics;
import com.unisinos.smart_health_data_core.alert.metrics.model.AlertMetrics;
import com.unisinos.smart_health_data_core.alert.model.Alert;
import com.unisinos.smart_health_data_core.alert.model.AlertScoreType;
import com.unisinos.smart_health_data_core.alert.service.AlertService;
import com.unisinos.smart_health_data_core.edge.model.Edge;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class AlertJobService {

	private final AlertService alertService;
	private final AlertMetricsService alertMetricsService;
	
	@Transactional
    @Scheduled(cron = "*/5 * * * * *")
	public void createAlertMetrics() {
		List<Alert> alerts = alertService.getAll();
		if (!alerts.isEmpty()) {
			AlertMetrics metrics = generateMetrics(alerts);
	        this.alertMetricsService.saveMetrics(metrics);
	        
	        List<AlertEdgeMetrics> edgeMetrics = generateEdgeMetrics(alerts);
	        this.alertMetricsService.saveAllEdgeMetrics(edgeMetrics);
	        
	        Date lastAlertDate = alerts.get(alerts.size() - 1).getDate();
			this.alertService.deleteAllProcessed(lastAlertDate);
		}
	}
	
	private AlertMetrics generateMetrics(List<Alert> alerts) {
		return alerts.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                            Alert::getScoreType,
                            Collectors.counting()
                        ),
                        counts -> {
                            int totalAlerts = alerts.size();

                            long distinctUsersCount = alerts.stream()
                                .map(Alert::getUserId)
                                .distinct()
                                .count();

                            AlertMetrics alertMetrics = new AlertMetrics();
                            alertMetrics.setId(UUID.randomUUID().toString());
                            alertMetrics.setTotal(totalAlerts);
                            alertMetrics.setSingleVitalSign(counts.getOrDefault(AlertScoreType.SINGLE_VITAL_SIGN, 0L).intValue());
                            alertMetrics.setMultiVitalSign(counts.getOrDefault(AlertScoreType.MULTI_VITAL_SIGN, 0L).intValue());
                            alertMetrics.setRegisteredAt(new Date());
                            alertMetrics.setUsers((int) distinctUsersCount);

                            return alertMetrics;
                        }
                    ));
	}
	
    private List<AlertEdgeMetrics> generateEdgeMetrics(List<Alert> alerts) {
        Map<String, Integer> edgeAlertCount = new HashMap<>();

        for (Alert alert : alerts) {
            String edgeId = alert.getEdge().getId();
            edgeAlertCount.put(edgeId, edgeAlertCount.getOrDefault(edgeId, 0) + 1);
        }

        List<AlertEdgeMetrics> edgeMetrics = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : edgeAlertCount.entrySet()) {
        	AlertEdgeMetrics metrics = new AlertEdgeMetrics();
        	metrics.setId(UUID.randomUUID().toString());
            metrics.setEdge(new Edge(entry.getKey()));
            metrics.setTotalAlerts(entry.getValue());
            metrics.setRegisteredAt(new Date());
            edgeMetrics.add(metrics);
        }
        return edgeMetrics;
    }
}
