package com.store.service;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class BillingConsumer {
	
	@KafkaListener(topics = {"PRODUCT_TOPIC"},groupId = "naimy_bouchaib")
	public void onMessage(String message){
	System.out.println("Consume ====>:"+message);
	}

}
