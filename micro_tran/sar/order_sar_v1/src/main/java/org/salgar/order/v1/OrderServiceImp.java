package org.salgar.order.v1;

import org.salgar.order.api.v1.OrderService;
import org.salgar.order.api.v1.model.Order;

public class OrderServiceImp implements OrderService {

	@Override
	public Order giveOrder(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String giveAlive() {
		return "We are alive!";
	}
}
