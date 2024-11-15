package com.unisinos.smart_health_data_core.commons.mqtt;

public class MqttTopicUtils {
	
	//public topics that receives vital signs from alert service
	public static final String VITAL_SIGN_TOPIC = "fog/private/vital-sign";
	
	//private topics that receives vital signs already processed from others fog nodes
	public static final String VITAL_SIGN_PROCESSED_TOPIC = "fog/private/processed/vital-sign";
	
	//private topics that receives metrics from others fog nodes
	public static final String ALERT_METRICS_TOPIC = "fog/private/metrics/alert";
	public static final String ALERT_EDGE_METRICS_TOPIC = "fog/private/metrics/alert-edge";
	
	//private topics that receives metrics from others fog nodes
	public static final String VITAL_SIGN_COUNT_METRICS_TOPIC = "fog/private/metrics/vital-sign-count";
	public static final String VITAL_SIGN_USER_METRICS_TOPIC = "fog/private/metrics/vital-sign-user";
	public static final String VITAL_SIGN_EDGE_METRICS_TOPIC = "fog/private/metrics/vital-sign-edge";
}
