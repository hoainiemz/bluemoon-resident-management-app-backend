package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Config {
    public static final String BANK_API_URL = "https://my.sepay.vn";
    public static final String BANK_API_TOKEN = "2FEONCJYTVGRK6DBM4XAAXN5STVEB7WECDJVXEDUHRO3LTAKWPVNLG71YQBAJHPT";

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + BANK_API_TOKEN);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
