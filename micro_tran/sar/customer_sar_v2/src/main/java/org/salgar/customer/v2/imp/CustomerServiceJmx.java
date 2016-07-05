package org.salgar.customer.v2.imp;

import org.salgar.customer.api.v2.CustomerService;
import org.salgar.customer.api.v2.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-customer-service-v2,type=org.salgar.customer.v2.imp.CustomerServiceImp,artifactId=salgar-customer-service-v2", log = true, logFile = "jmx.log")
public class CustomerServiceJmx implements CustomerService {
	@Autowired
	private CustomerService customerService;

	@Override
	@ManagedOperation(description = "Gets a parameter as String and delivers an Customer")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="customerId", description="Id of the customer that we want to load.")
    })
	public Customer giveCustomer(Integer customerId) {
		return customerService.giveCustomer(customerId);
	}

	@Override
	@ManagedOperation(description = "Gets a parameter as Customer object and persists it!")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="customer", description="Customer object that we want to persist.")
    })
	public Customer saveCustomer(Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@Override
	@ManagedOperation(description = "Returns the give alive signal!")
	public String giveAlive() {
		return customerService.giveAlive();
	}
}