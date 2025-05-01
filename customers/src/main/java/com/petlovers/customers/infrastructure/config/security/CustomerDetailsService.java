package com.petlovers.customers.infrastructure.config.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petlovers.customers.domain.repository.CustomerRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return customerRepository.findByEmail(email)
				.map(customer -> new org.springframework.security.core.userdetails.User(customer.getEmail(), "",
						new ArrayList<>()))
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

}
