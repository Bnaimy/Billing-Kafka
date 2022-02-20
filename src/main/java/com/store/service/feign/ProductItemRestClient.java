package com.store.service.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.store.service.model.Product;

@FeignClient(name = "INVENTORYSERVICE")
public interface ProductItemRestClient {
    @GetMapping(path = "/products")
    public PagedModel<Product> pageProducts(@RequestParam(name = "page") int page , @RequestParam(name = "size") int size);
    @GetMapping(path = "/products/{id}")
    public Product getProductById(@PathVariable Long id);
}
