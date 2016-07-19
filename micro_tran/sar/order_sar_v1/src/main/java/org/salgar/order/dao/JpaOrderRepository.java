package org.salgar.order.dao;

import org.salgar.order.api.model.Order;
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
	public Order saveOrder(Order order) {
		return entityManager.merge(order);
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
