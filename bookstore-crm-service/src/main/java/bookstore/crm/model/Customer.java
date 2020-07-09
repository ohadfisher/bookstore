package bookstore.crm.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Customer {
    private String name;
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, Set<Order> orders) {
        this.name = name;
        this.orders = orders;
    }


    public String getName() {
        return name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order){
        if(order!=null)
            orders.add(order);
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }

    public void removeAllOrders(){
        orders.clear();
    }

    public void removeOrdersByBookIds(Set<Long> bookIds){
        orders = orders.stream()
                .filter(order -> !bookIds.contains(order.getBookDetails().getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!name.equals(customer.name)) return false;
        return orders != null ? orders.equals(customer.orders) : customer.orders == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    public static class CustomerBuilder {
        private String name;
        private Set<Order> orders;

        public CustomerBuilder setName(String name) {
            this.name = name;
            return this;
        }


        public CustomerBuilder setOrders(Set<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Customer build() {
            return new Customer(name,orders);
        }
    }
}
