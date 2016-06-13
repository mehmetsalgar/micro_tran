package org.salgar.product.v1.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.salgar.product.api.v1.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class JpaProductRepository implements ProductRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Product findById(Integer id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	public void saveProduct(Product product) {
		entityManager.merge(product);
	}
}