package org.salgar.customer.v1.dao;

import org.salgar.customer.api.v1.model.Customer;

public interface CustomerRepository {
	Customer findById(Integer customerId);
	void saveCustomer(Customer customer);
}