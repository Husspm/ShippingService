package com.company.shippingcrudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShippingCrudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingCrudServiceApplication.class, args);
	}

}
