package org.salgar.order.imp;

import org.salgar.order.api.OrderService;
import org.salgar.order.api.model.Order;
import org.salgar.order.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderServiceImp implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Override
	@Transactional(readOnly = true)
	public Order giveOrder(Integer orderId) {
		return orderRepository.findOrderByd(orderId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Order saveOrder(Order order) {
		return orderRepository.saveOrder(order);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NEVER)
	public String giveAlive() {
		return alive_signal;
	}
}