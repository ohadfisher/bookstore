package bookstore.crm.controller;

import bookstore.crm.model.Book;
import org.apache.log4j.Logger;
import bookstore.crm.model.Customer;
import bookstore.crm.model.Order;
import bookstore.crm.service.CrmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/crm/")
public class CrmController {
    static Logger log = Logger.getLogger(CrmController.class.getName());


    private CrmServices crmServices;

    @Autowired
    public CrmController(CrmServices crmServices) {
        this.crmServices = crmServices;
    }


    @PostMapping("/addCustomer")
    Boolean addCustomer(@RequestBody String customerName) {
        return crmServices.addConsumer(customerName);
    }


    @GetMapping("/getCustomerByName/{customerName}")
    public Optional<Customer> getCustomerByName(@PathVariable("customerName") String customerName) {
        return crmServices.getCustomer(customerName);
    }


    @PostMapping("/addOrderToCustomer/{customerName}")
    public Optional<Long> addOrderToCustomer(@PathVariable("customerName") String customerName,
                                   @RequestBody Book book) {
        return crmServices.addOrderToCustomer(customerName,book);
    }


    @PutMapping("/removeAllOrdersFromCustomer") //Use put and not delete because the Customer is update
    public void removeAllOrdersFromCustomer(@RequestBody String customerName) {
        crmServices.removeAllOrdersFromCustomer(customerName);
    }


    @DeleteMapping("/removeCustomer/{customerName}")
    public void removeCustomer(@PathVariable("customerName") String customerName) {
        crmServices.removeCustomer(customerName);
    }


    @GetMapping("/getOrder/{customerName}/{orderId}")
    Optional<Order> getOrder(@PathVariable("orderId") Long orderId,
                             @PathVariable("customerName") String customerName){
        return crmServices.getOrder(customerName, orderId);
    }


}
