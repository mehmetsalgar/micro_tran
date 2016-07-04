package org.salgar.service.rest;

import javax.inject.Named;

import org.salgar.customer.api.v2.CustomerService;
import org.salgar.customer.api.v2.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerServiceRest {
	@Autowired
	@Named("proxyCustomerService")
	private CustomerService customerService;
	
	@RequestMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer resut = customerService.giveCustomer(customerId);
		return resut;
	}

	@RequestMapping(path = "/saveCustomer", method = RequestMethod.POST)
	public Customer saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
}