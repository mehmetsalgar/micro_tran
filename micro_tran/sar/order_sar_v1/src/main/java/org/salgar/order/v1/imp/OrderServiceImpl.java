package org.salgar.order.v1.imp;

import org.salgar.order.api.v1.OrderService;
import org.salgar.order.api.v1.model.Order;
import org.salgar.order.v1.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NEVER)
	public String giveAlive() {
		return "We are alive!";
	}

	@Override
	@Transactional(readOnly = true)
	public Order giveOrder(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveOrder(Order order) {
		orderRepository.saveOrder(order);
	}
}