package org.salgar.customer.v2.dao;

import org.salgar.customer.api.v2.model.Customer;

public interface CustomerRepository {
	Customer findCustomerById(Integer customerId);
	Customer saveCustomer(Customer customer);
}
