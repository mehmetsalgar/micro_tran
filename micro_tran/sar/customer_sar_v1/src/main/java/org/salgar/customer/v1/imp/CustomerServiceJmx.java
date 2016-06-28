package org.salgar.customer.v1.imp;

import org.salgar.customer.api.v1.CustomerService;
import org.salgar.customer.api.v1.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-customer-service-v1,type=org.salgar.customer.v1.imp.CustomerServiceImp,artifactId=salgar-customer-service-v1", log = true, logFile = "jmx.log")
public class CustomerServiceJmx implements CustomerService {
	@Autowired
	private CustomerService customerService;

	@Override
	@ManagedOperation(description = "Keep alive test")
    @ManagedOperationParameters()
	public String giveAlive() {
		return customerService.giveAlive();
	}

	@Override
	@ManagedOperation(description = "Gets a parameter as String and delivers an Customer")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="customerId", description="Id of the customer that we want to load.")
    })
	public Customer giveCustomer(Integer customerId) {
		return customerService.giveCustomer(customerId);
	}

	@Override
	@ManagedOperation(description = "Saves a customer object")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="customer", description="Customer that we want to save.")
    })
	public Customer saveCustomer(Customer customer) {
		return customerService.saveCustomer(customer);
	}
}