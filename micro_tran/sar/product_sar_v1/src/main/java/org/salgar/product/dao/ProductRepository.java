package org.salgar.product.dao;

import org.salgar.product.api.model.Product;

public interface ProductRepository {
	Product findById(Integer id);
	Product saveProduct(Product product);	
}
