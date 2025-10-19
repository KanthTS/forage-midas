package com.jpmc.midascore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;

@Service
public class IncentiveService {

	private final RestTemplate restTemplate = new RestTemplate();
	
	public Incentive inc(Transaction t) {
		String url = "http://localhost:33400/incentive";
		Incentive incentive = restTemplate.postForObject(url, t, Incentive.class);
		if (incentive != null) { System.out.println("Incentive received: " + incentive.getAmount()); return incentive; } else { System.out.println("No incentive received, setting to 0"); return new Incentive(0); }
	}
}
