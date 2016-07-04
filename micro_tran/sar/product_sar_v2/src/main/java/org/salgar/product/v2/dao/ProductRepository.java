package org.salgar.product.v2.dao;

import org.salgar.product.api.v2.model.Product;

public interface ProductRepository {
	Product findById(Integer id);
	Product saveProduct(Product product);
}