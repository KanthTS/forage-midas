package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Balance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BalanceQuerier {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:33400/balance";

    public Balance query(long userId) {
        String url = baseUrl + "?userId=" + userId;
        return restTemplate.getForObject(url, Balance.class);
    }
}
