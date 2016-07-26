package org.salgar.customer.api;

import org.salgar.customer.api.model.Customer;
import org.salgar.healthcheck.HealthCheck;

public interface CustomerService extends HealthCheck {
	Customer giveCustomer(Integer customerId);
	Customer saveCustomer(Customer customer);
}