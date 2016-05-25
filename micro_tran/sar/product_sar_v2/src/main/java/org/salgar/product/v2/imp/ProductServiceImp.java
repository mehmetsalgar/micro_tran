package org.salgar.product.v2.imp;
import org.salgar.product.api.v2.ProductService;
import org.salgar.product.api.v2.model.Product;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-product-service-v2,type=org.salgar.product.v2.imp.ProductServiceImp,artifactId=salgar-product-service-v2", description = "Product Service", log = true, logFile = "jmx.log")
public class ProductServiceImp implements ProductService {

	@Override
	@ManagedOperation(description = "Gets a parameter as String")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="productId", description="Id of the product that we want to load.")
    })
	public Product giveProduct(Integer productId) {
		Product result = new Product();
		result.setProductId(2);
		result.setName("topProduct2");
		result.setQuality("magnificient");
		return new Product();
	}

	@Override
	@ManagedOperation(description = "Delivers we are alive message!")
    @ManagedOperationParameters()
	public String giveAlive() {
		// TODO Auto-generated method stub
		return "Test: we are alive!";
	}

	@Override
	@ManagedOperation(description = "Delivers we are alive message 2!")
    @ManagedOperationParameters()
	public String giveAlive2() {
		// TODO Auto-generated method stub
		return "Test2: we are alive!";
	}
}
