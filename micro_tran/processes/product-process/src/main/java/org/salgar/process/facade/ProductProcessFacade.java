package org.salgar.process.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProductProcessFacade {
	org.salgar.product.api.v1.model.Product getProductV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product getProductV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v1.model.Product executeFallBackV1(int productId)
			throws JsonParseException, JsonMappingException, IOException;

	org.salgar.product.api.v2.model.Product executeFallBackV2(int productId)
			throws JsonParseException, JsonMappingException, IOException;

}