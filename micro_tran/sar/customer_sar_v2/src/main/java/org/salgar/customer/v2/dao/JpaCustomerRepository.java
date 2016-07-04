package org.salgar.customer.v2.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.salgar.customer.api.v2.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCustomerRepository implements CustomerRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Customer findCustomerById(Integer customerId) {
		return entityManager.find(Customer.class, customerId);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return entityManager.merge(customer);
	}
}