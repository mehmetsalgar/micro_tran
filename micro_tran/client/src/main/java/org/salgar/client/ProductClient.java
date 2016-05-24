package org.salgar.client;

import javax.inject.Named;

import org.salgar.product.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Controller
public class ProductClient {
	@Autowired
	@Named("proxyProductService")
	private ProductService productService;
	
	@RequestMapping("test")
	public String giveHealthCheck() {
		String result = productService.giveAlive2();
		
		if(result != null && !"".equals(result)) {
			return "true";
		}
		return "false";
	}
}
