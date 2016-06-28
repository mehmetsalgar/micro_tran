package org.salgar.product.api.v1;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.product.api.v1.model.Product;

public interface ProductService extends HealthCheck {
	Product giveProduct(Integer productId);
	Product saveProduct(Product product);
}