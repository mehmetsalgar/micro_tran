package org.salgar.service.rest;

import javax.inject.Named;

import org.salgar.product.api.v1.ProductService;
import org.salgar.product.api.v1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceRest {
	@Autowired
	@Named("proxyProductService")
	private ProductService productService;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/product/{productId}")
	public Product getProduct(@PathVariable int productId) {
		Product resut = productService.giveProduct(1);
		return resut;
	}
}