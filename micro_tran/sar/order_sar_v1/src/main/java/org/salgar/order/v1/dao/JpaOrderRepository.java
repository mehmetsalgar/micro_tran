package org.salgar.order.v1.dao;

import org.salgar.order.api.v1.model.Order;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
