package org.salgar.process.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProcessFacade {
	org.salgar.product.api.v1.model.Product getProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product getProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v1.model.Product executeFallBackProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product executeFallBackProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order getOrderV1(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	org.salgar.order.api.v1.model.Order executeFallBackOrderV1(int orderId)
			throws JsonParseException, JsonMappingException, IOException;
	
	void saveOrderV1(org.salgar.order.api.v1.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
	
	void executeFallBackSaveOrderV1(org.salgar.order.api.v1.model.Order order)
			throws JsonParseException, JsonMappingException, IOException;
}