package org.salgar.customer.dao;

import org.salgar.customer.api.model.Customer;

public interface CustomerRepository {
	Customer findCustomerById(Integer customerId);
	Customer saveCustomer(Customer customer);
}
