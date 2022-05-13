package com.fdm.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.project.model.Currency;
import com.fdm.project.repo.CurrencyRepo;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepo currencyRepo;
	
	public List<Currency> getAllCurrencies(){
		return currencyRepo.findAll();
	}

	public Currency getByCurrencyCode(String preferredcurrency) {
		return currencyRepo.getByCurrencyCode(preferredcurrency);
	}

}
