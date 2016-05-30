package org.salgar.service.healthchecker;

import javax.inject.Named;

import org.salgar.product.api.v1.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RestHealthIndicatorV1 implements HealthIndicator{
	@Autowired
	@Named("proxyProductService")
	private ProductService productService;
	
	@Override
	public Health health() {
		try {
			String result = productService.giveAlive();
			if(result == null || "".equals(result)) {
				return Health.down().withDetail("result", result).build();
			}
		} catch (Throwable t) {
			return Health.down().withDetail(t.getMessage(), t).build();
		}
		return Health.up().build();
	}

}
