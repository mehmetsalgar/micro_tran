package org.salgar.healthcheck;

import java.util.List;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class ProcessHealthIndicator implements HealthIndicator {
	List<? extends HealthCheck> services;
	
	
	public ProcessHealthIndicator(List<? extends HealthCheck> services) {
		this.services = services;
	}

	@Override
	public Health health() {
		for (HealthCheck service : services) {
			try {
				String result = service.giveAlive();
				if(result == null || "".equals(result)) {
					return Health.down().withDetail("result", result).build();
				}
			} catch (Throwable t) {
				return Health.down().withDetail("" + t.getMessage(), t).build();
			}
		}
		
		return Health.up().build();
	}
}