package org.salgar.customer.api.v2;

import org.salgar.customer.api.v2.model.Customer;
import org.salgar.healthcheck.HealthCheck;

public interface CustomerService extends HealthCheck {
	Customer giveCustomer(Integer customerId);
	Customer saveCustomer(Customer customer);
}