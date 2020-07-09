package bookstore.crm.repository;

import org.apache.log4j.Logger;

import bookstore.crm.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CrmCustomers {
    private static Logger log = Logger.getLogger(CrmCustomers.class.getName());

    private Map<String, Customer> customerMap = new HashMap<>();



    public boolean addCustomer(Customer customer){
        try {
            customerMap.put(customer.getName(),customer);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage(),e);
            return false;
        }
    }

    public Customer getCustomer(String customerName){
        return customerMap.get(customerName);
    }


    public boolean isCustomerExist(String customerName) {
        return customerMap.containsKey(customerName);
    }

    public void removeCustomer(String customerName) {
        customerMap.remove(customerName);
    }

}
