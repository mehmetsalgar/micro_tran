package org.salgar.service.rest;

import javax.inject.Named;

import org.salgar.product.api.ProductService;
import org.salgar.product.api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		Product resut = productService.giveProduct(productId);
		return resut;
	}
	
	@RequestMapping(path = "/product/saveProduct", method = RequestMethod.POST)
	public Product saveProduct(@RequestBody Product product) {
		Product resut = productService.saveProduct(product);
		return resut;
	}
}