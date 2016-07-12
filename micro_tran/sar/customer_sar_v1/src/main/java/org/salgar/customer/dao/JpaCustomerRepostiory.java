package org.salgar.customer.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.salgar.customer.api.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCustomerRepostiory implements CustomerRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Customer findById(Integer customerId) {
		return entityManager.find(Customer.class, customerId);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return entityManager.merge(customer);
	}
}