package org.salgar.product.v2.imp;

import org.salgar.product.api.v2.ProductService;
import org.salgar.product.api.v2.model.Product;
import org.salgar.product.v2.dao.ProductRepository;
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
	@Transactional(readOnly = true)
	public Product giveProduct(Integer productId) {
		return productRepository.findById(productId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Product saveProduct(Product product) {
		return productRepository.saveProduct(product);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NEVER)
	public String giveAlive() {
		return alive_signal;
	}
}