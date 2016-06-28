package org.salgar.product.v1.dao;

import org.salgar.product.api.v1.model.Product;

public interface ProductRepository {
	Product findById(Integer id);
	Product saveProduct(Product product);	
}
