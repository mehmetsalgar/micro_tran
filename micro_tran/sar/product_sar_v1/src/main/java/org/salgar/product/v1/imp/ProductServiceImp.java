package org.salgar.product.v1.imp;
import org.salgar.product.api.v1.ProductService;
import org.salgar.product.api.v1.model.Product;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-product-service-v1,type=org.salgar.product.v1.imp.ProductServiceImp,artifactId=salgar-product-service-v1", description = "Product Service V1", log = true, logFile = "jmx.log")
public class ProductServiceImp implements ProductService {

	@Override
	@ManagedOperation(description = "Gets a parameter as String")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="productId", description="Id of the product that we want to load.")
    })
	public Product giveProduct(Integer productId) {
		Product result = new Product();
		result.setName("topProduct1");
		result.setProductId(1);
		return result;
	}

	@Override
	@ManagedOperation(description = "Delivers we are alive message!")
    @ManagedOperationParameters()
	public String giveAlive() {
		return "Test: we are alive!";
	}
}
