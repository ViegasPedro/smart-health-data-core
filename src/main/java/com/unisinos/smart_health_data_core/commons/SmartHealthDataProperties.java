package com.unisinos.smart_health_data_core.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "unisinos.smart-health-data")
public class SmartHealthDataProperties {

	private Mqtt mqtt;
	
	@Getter
	@Setter
	public static class Mqtt {
		private String serverUrl;
		private String clientId;
		private String user;
		private String password;
	}
	
}
