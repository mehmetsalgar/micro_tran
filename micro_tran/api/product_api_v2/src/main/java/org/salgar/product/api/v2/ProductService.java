package org.salgar.product.api.v2;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.product.api.v2.model.Product;

public interface ProductService extends HealthCheck {
	Product giveProduct(Integer productId);
	String giveAlive();
	String giveAlive2();
}