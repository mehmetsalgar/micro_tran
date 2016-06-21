package org.salgar.product.v1.imp;

import org.salgar.product.api.v1.ProductService;
import org.salgar.product.api.v1.model.Product;
import org.salgar.product.v1.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NEVER)
	public String giveAlive() {
		return alive_signal;
	}

	@Override
	@Transactional(readOnly = true)
	public Product giveProduct(Integer productId) {
		return productRepository.findById(productId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveProduct(Product product) {
		productRepository.saveProduct(product);
	}
}