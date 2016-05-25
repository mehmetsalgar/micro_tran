package org.salgar.product.api.v1;

import org.salgar.product.api.v1.model.Product;

public interface ProductService {
	Product giveProduct(Integer productId);
	String giveAlive();
}