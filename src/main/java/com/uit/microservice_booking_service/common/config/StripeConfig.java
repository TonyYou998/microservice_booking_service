package com.uit.microservice_booking_service.common.config;

import com.stripe.Stripe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    private final Logger LOGGER= LoggerFactory.getLogger(StripeConfig.class);
    @Value("sk_test_51MFgtADG7fwzFQTe8OyidrYMyAe45WaFn3hDvGTW5nKm5AtbImyjucKZEKlBOKmfJGzGGiSSAgp2WofGNNWoOOiC00CZdCo6sa")
    private String secretKey;

    @Value("pk_test_51MFgtADG7fwzFQTedvRdnq6UjWj3ylpqDTjYQKzwmE7yHp8WpZfvrl67VYYFHXdPMynx6IwSCUJx06BAhzjba9ul00kyH4JStu")
    private String publishableKey;
    @Bean
    public void stripe() {
        Stripe.apiKey = secretKey;
        LOGGER.info(secretKey);

    }
//
    @Bean
    public String publishableKey() {
        return publishableKey;
    }
}
