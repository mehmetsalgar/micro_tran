package org.salgar.product.api;

import org.salgar.product.api.model.Product;

public interface ProductService {
	Product giveProduct(Integer productId);
	String giveAlive();
	String giveAlive2();
}