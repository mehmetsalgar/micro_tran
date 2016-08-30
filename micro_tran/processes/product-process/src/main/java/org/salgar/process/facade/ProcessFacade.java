package org.salgar.process.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProcessFacade {
	org.salgar.product.api.model.Product giveProduct(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.model.Product executeFallBackProduct(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.model.Product saveProduct(org.salgar.product.api.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.model.Product executeFallBackSaveProduct(org.salgar.product.api.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.model.Customer giveCustomer(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.model.Customer executeFallBackCustomer(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.model.Customer saveCustomer(org.salgar.customer.api.model.Customer customer)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.model.Customer executeFallBackSaveCustomer(org.salgar.customer.api.model.Customer product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.model.Order giveOrder(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.model.Order executeFallBackOrder(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.model.Order saveOrder(org.salgar.order.api.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.model.Order executeFallBackSaveOrder(org.salgar.order.api.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
}