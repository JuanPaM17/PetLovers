package com.petlovers.customers.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petlovers.customers.domain.model.Customer;
import com.petlovers.customers.domain.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public Optional<Customer> getByEmail(String email) {
		return repository.findByEmail(email);
	}

	public Customer createCustomer(Customer customer) {
		return repository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}

	public Optional<Customer> getCustomerById(Long id) {
		return repository.findById(id);
	}

}
