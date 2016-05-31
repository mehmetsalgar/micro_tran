package org.salgar.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class RestHealthIndicator<T extends HealthCheck> implements HealthIndicator {
	private T service;
	
	public RestHealthIndicator(T service) {
		this.service = service;
	}
	
	@Override
	public Health health() {
		try {
			String result = service.giveAlive();
			if(result == null || "".equals(result)) {
				return Health.down().withDetail("result", result).build();
			}
		} catch (Throwable t) {
			return Health.down().withDetail("" + t.getMessage(), t).build();
		}
		return Health.up().build();
	}
}
