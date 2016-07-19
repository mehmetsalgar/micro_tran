package org.salgar.order.imp;

import java.util.List;

import org.salgar.order.api.OrderService;
import org.salgar.order.api.model.Order;
import org.salgar.order.dao.OrderRepository;
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
		return alive_signal;
	}

	@Override
	@Transactional(readOnly = true)
	public Order giveOrder(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Order saveOrder(Order order) {
		return orderRepository.saveOrder(order);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Order> giveCustomerOrders(Integer customerId) {
		List<Order> results = orderRepository.giveCustomerOrders(customerId);
		
		return results;
	}
}