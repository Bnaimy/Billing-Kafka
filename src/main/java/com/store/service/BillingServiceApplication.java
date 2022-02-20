package com.store.service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.PagedModel;
import com.store.service.entities.Bill;
import com.store.service.entities.ProductItem;
import com.store.service.feign.CustomerRestClient;
import com.store.service.feign.ProductItemRestClient;
import com.store.service.model.Customer;
import com.store.service.model.Product;
import com.store.service.repository.BillRepository;
import com.store.service.repository.ProductItemRepository;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository  ,
                            ProductItemRepository productItemRepository ,
                            CustomerRestClient customerRestClient,
                            ProductItemRestClient productItemRestClient,
                            RepositoryRestConfiguration repositoryRestConfiguration){

        repositoryRestConfiguration.exposeIdsFor(Bill.class);
        return args -> {

            Customer customer = customerRestClient.getCustomerById(1L);
            Bill bill1 = billRepository.save(new Bill(null , new Date() , null , customer.getId() , null));
            PagedModel<Product>  productPagedModel = productItemRestClient.pageProducts(0 , 3);
            productPagedModel.forEach(
                    product -> {
                        ProductItem productItem = new ProductItem();
                        productItem.setPrice(product.getPrice());
                        productItem.setQuantity(1+ new Random().nextInt(100));
                        productItem.setBill(bill1);
                        productItem.setProductID(product.getId());
                        productItemRepository.save(productItem);
                    }
            );
            //System.out.println(customer);


        };
    }

}