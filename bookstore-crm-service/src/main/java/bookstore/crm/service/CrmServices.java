package bookstore.crm.service;

import bookstore.crm.model.Book;
import bookstore.crm.model.Customer;
import bookstore.crm.model.Order;
import bookstore.crm.repository.CrmCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CrmServices {

    private CrmCustomers crmCustomers;

    @Autowired
    public CrmServices(CrmCustomers crmCustomers) {
        this.crmCustomers = crmCustomers;
    }

    public Optional<Customer> getCustomer(String customerName) {
        if (isCustomerExist(customerName)) {
            return Optional.of(crmCustomers.getCustomer(customerName));
        }else{
            return Optional.empty();
        }
    }

    public boolean addConsumer(String customerName) {
        if (customerName != null && !customerName.isEmpty() && !isCustomerExist(customerName)) {
            return crmCustomers.addCustomer(new Customer(customerName));
        }
        return false;
    }

    public Optional<Long> addOrderToCustomer(String customerName, Book book) {

        Order order = new Order(customerName, book);
        try {
            getCustomer(customerName).get().addOrder(order);
        }catch (NoSuchElementException e){
            return Optional.empty();
        }
        return Optional.of(order.getOrderId());
    }



    public void removeAllOrdersFromCustomer(String customerName)  {
        Optional<Customer> optionalCustomer = getCustomer(customerName);
        if (optionalCustomer.isPresent()) {
            optionalCustomer.get().removeAllOrders();
        }
    }


    public void removeCustomer(String customerName) {

        crmCustomers.removeCustomer(customerName);
    }

    public Optional<Order> getOrder(String customerName, Long orderId) {
        try{
            return getCustomer(customerName).get().getOrders().stream()
                    .filter(order -> order.getOrderId().equals(orderId)).findAny();
        }catch (NoSuchElementException e){
            return Optional.empty();
        }
    }

    private boolean isCustomerExist(String customerName) {
        return crmCustomers.isCustomerExist(customerName);
    }
}
