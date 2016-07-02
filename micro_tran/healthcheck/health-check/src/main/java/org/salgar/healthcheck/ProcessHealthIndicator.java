package org.salgar.healthcheck;

import java.util.List;

import org.springframework.boot.actuate.health.HealthIndicator;

public interface ProcessHealthIndicator extends HealthIndicator {
	void setServices(List<? extends HealthCheck> services);
}
