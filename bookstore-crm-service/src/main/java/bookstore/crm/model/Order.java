package bookstore.crm.model;

import java.time.LocalDate;

public class Order {

    private static Long orderIdCounter =0L;

    private Long orderId;
    private String customerName;
    private Book bookDetails;

    private LocalDate date;

    public Order() {
    }

    public Order(String customerName, Book book) {

        this.orderId = orderIdCounter++;
        this.customerName = customerName;
        this.date = LocalDate.now();
        this.bookDetails = book;
    }

    public static Long getOrderIdCounter() {
        return orderIdCounter;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Book getBookDetails() {
        return bookDetails;
    }

    public LocalDate getDate() {
        return date;
    }

}
