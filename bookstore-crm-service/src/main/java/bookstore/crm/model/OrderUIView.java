package bookstore.crm.model;

import java.time.LocalDate;

public class OrderUIView {


    private String customerName;
    private String bookName;
    private String bookAuthor;
    private LocalDate date;

    public OrderUIView() {
    }

    public OrderUIView(String customerName, String bookName, String bookAuthor, LocalDate date) {
        this.customerName = customerName;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.date = date;
    }


    public static class Builder {
        private String customerName;
        private String bookName;
        private String bookAuthor;
        private LocalDate date;

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setBookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public OrderUIView build() {
            return new OrderUIView(customerName, bookName, bookAuthor, date);
        }
    }
}
