package org.salgar.process.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProcessFacade {
	org.salgar.product.api.v1.model.Product giveProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.v1.model.Product executeFallBackProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.v1.model.Product saveProductV1(org.salgar.product.api.v1.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.v1.model.Product executeFallBackSaveProductV1(org.salgar.product.api.v1.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product giveProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product executeFallBackProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.v2.model.Product saveProductV2(org.salgar.product.api.v2.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.product.api.v2.model.Product executeFallBackSaveProductV2(org.salgar.product.api.v2.model.Product product)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order giveOrderV1(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order executeFallBackGiveOrderV1(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v2.model.Order giveOrderV2(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v2.model.Order executeFallBackGiveOrderV2(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order saveOrderV1(org.salgar.order.api.v1.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order executeFallBackSaveOrderV1(org.salgar.order.api.v1.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v2.model.Order saveOrderV2(org.salgar.order.api.v2.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v2.model.Order executeFallBackSaveOrderV2(org.salgar.order.api.v2.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v1.model.Customer saveCustomerV1(org.salgar.customer.api.v1.model.Customer customer)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v1.model.Customer executeFallBackSaveCustomerV1(org.salgar.customer.api.v1.model.Customer customer)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v1.model.Customer giveCustomerV1(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v1.model.Customer executeFallBackGiveCustomerV1(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v2.model.Customer giveCustomerV2(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.customer.api.v2.model.Customer executeFallBackGiveCustomerV2(int customerId)
			throws JsonParseException, JsonMappingException, IOException;
}