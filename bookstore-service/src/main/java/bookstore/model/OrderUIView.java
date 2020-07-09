package bookstore.model;

import java.time.LocalDate;

public class OrderUIView {

    private String customerName;
    private String bookName;
    private String bookAuthor;
    private LocalDate date;

    public OrderUIView() {
    }

    public OrderUIView(Order order) {
        this.customerName = order.getCustomerName();
        this.bookName = order.getBookDetails().getName();
        this.bookAuthor = order.getBookDetails().getAuthor();
        this.date = order.getDate();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public LocalDate getDate() {
        return date;
    }
}
