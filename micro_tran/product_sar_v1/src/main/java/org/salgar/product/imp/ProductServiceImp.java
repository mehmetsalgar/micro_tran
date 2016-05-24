package org.salgar.product.imp;
import org.salgar.product.api.ProductService;
import org.salgar.product.api.model.Product;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-product-service,type=org.salgar.product.imp.ProductServiceImp,artifactId=salgar-product-service", description = "Product Service", log = true, logFile = "jmx.log")
public class ProductServiceImp implements ProductService {

	@Override
	@ManagedOperation(description = "Gets a parameter as String")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="productId", description="Id of the product that we want to load.")
    })
	public Product giveProduct(Integer productId) {
		// TODO Auto-generated method stub
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
