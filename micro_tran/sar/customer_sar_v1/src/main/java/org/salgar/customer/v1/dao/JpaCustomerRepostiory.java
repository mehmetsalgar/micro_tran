package org.salgar.customer.v1.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.salgar.customer.api.v1.model.Customer;
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