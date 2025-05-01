package com.petlovers.billing.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petlovers.billing.domain.model.Bill;
import com.petlovers.billing.domain.repository.BillRepository;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	public Bill create(Bill bill) {
		return billRepository.save(bill);
	}

	public List<Bill> getAll() {
		return billRepository.findAll();
	}

	public Optional<Bill> getById(Long id) {
		return billRepository.findById(id);
	}

	public Bill update(Long id, Bill updated) {
		return billRepository.findById(id).map(existing -> {
			existing.setIssuedDate(updated.getIssuedDate());
			existing.setTotal(updated.getTotal());
			existing.setDescription(updated.getDescription());
			return billRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Bill not found"));
	}

	public void delete(Long id) {
		billRepository.deleteById(id);
	}

}
