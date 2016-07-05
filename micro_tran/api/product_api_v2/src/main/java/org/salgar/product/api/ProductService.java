package org.salgar.product.api;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.product.api.model.Product;

public interface ProductService extends HealthCheck {
	Product giveProduct(Integer productId);
	Product saveProduct(Product product);
}