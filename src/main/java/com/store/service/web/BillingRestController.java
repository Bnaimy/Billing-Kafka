package com.store.service.web;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.store.service.entities.Bill;
import com.store.service.feign.CustomerRestClient;
import com.store.service.feign.ProductItemRestClient;
import com.store.service.model.Customer;
import com.store.service.model.Product;
import com.store.service.repository.BillRepository;
import com.store.service.repository.ProductItemRepository;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path ="/fullBill/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            Product product=productItemRestClient.getProductById(pi.getProductID());
            pi.setProduct(product);
        });
        return bill;
    }
}