package com.store.service.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.store.service.model.Customer;

@FeignClient(name = "CUSTOMERSERVICE")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable(name = "id") Long id);
}
