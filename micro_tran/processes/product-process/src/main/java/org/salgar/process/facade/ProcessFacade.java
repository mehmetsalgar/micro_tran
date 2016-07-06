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
}