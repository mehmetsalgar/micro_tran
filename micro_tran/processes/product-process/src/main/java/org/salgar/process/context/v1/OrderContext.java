package org.salgar.process.context.v1;

public class OrderContext {
	org.salgar.customer.api.v1.model.Customer customer;
	org.salgar.product.api.v1.model.Product product;
	org.salgar.order.api.v1.model.Order order;

	public org.salgar.customer.api.v1.model.Customer getCustomer() {
		return customer;
	}

	public void setCustomer(org.salgar.customer.api.v1.model.Customer customer) {
		this.customer = customer;
	}

	public org.salgar.product.api.v1.model.Product getProduct() {
		return product;
	}

	public void setProduct(org.salgar.product.api.v1.model.Product product) {
		this.product = product;
	}

	public org.salgar.order.api.v1.model.Order getOrder() {
		return order;
	}

	public void setOrder(org.salgar.order.api.v1.model.Order order) {
		this.order = order;
	}
}
