package org.salgar.product.imp;

import org.salgar.product.api.ProductService;
import org.salgar.product.api.model.Product;
import org.salgar.product.dao.ProductRepository;
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
	public Product saveProduct(Product product) {
		return productRepository.saveProduct(product);
	}
}