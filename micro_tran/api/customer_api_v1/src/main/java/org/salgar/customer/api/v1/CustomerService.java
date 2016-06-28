package org.salgar.customer.api.v1;

import org.salgar.customer.api.v1.model.Customer;
import org.salgar.healthcheck.HealthCheck;

public interface CustomerService extends HealthCheck {
	Customer giveCustomer(Integer customerId);
	Customer saveCustomer(Customer customer);
}