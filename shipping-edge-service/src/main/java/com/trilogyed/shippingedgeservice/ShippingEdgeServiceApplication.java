package com.trilogyed.shippingedgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class ShippingEdgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingEdgeServiceApplication.class, args);
	}

}
