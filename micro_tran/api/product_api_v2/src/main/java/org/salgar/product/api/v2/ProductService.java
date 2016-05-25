package org.salgar.product.api.v2;

import org.salgar.product.api.v2.model.Product;

public interface ProductService {
	Product giveProduct(Integer productId);
	String giveAlive();
	String giveAlive2();
}