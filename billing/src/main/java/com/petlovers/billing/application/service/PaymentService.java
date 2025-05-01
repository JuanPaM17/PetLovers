package com.petlovers.billing.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petlovers.billing.domain.model.Payment;
import com.petlovers.billing.domain.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	public Payment create(Payment payment) {
		return paymentRepository.save(payment);
	}

	public List<Payment> getAll() {
		return paymentRepository.findAll();
	}

	public Optional<Payment> getById(Long id) {
		return paymentRepository.findById(id);
	}

	public void delete(Long id) {
		paymentRepository.deleteById(id);
	}

}
