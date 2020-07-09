package bookstore.model;

import java.time.LocalDate;

public class Order {


    private Long orderId;
    private String customerName;
    private Book bookDetails;

    private LocalDate date;

    public Order() {
    }

    public Order(Long orderId, String customerName, Book bookDetails, LocalDate date) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.bookDetails = bookDetails;
        this.date = date;
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

