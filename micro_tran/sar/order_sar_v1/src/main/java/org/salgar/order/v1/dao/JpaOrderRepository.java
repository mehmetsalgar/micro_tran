package org.salgar.order.v1.dao;

import org.salgar.order.api.v1.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaOrderRepository implements OrderRepository  {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void saveOrder(Order order) {
		entityManager.merge(order);
	}

	@Override
	public Order findById(Integer id) {
		return entityManager.find(Order.class, id);
	}

	@Override
	public List<Order> giveCustomerOrders(Integer customerId) {
		Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.customer.id= :id ");
		@SuppressWarnings("unchecked")
		List<Order> results = (List<Order>) query.setParameter("id", customerId).getResultList();
		return results;
	}
}
