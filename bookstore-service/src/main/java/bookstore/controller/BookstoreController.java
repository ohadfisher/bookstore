package bookstore.controller;

import bookstore.model.Book;
import bookstore.model.Books;
import bookstore.model.Customer;
import bookstore.model.OrderUIView;
import bookstore.model.BookIds;

import bookstore.service.BookstoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookstore/")
public class BookstoreController {
    private final BookstoreServices bookstoreServices;

    @Autowired
    public BookstoreController(BookstoreServices bookstoreServices) {
        this.bookstoreServices = bookstoreServices;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookstoreServices.getBookById(id);
    }

    @GetMapping("/getBooksByName/{name}")
    public Books getBooksByName(@PathVariable("name") String name) {
        return bookstoreServices.getBooksByName(name);
    }

    @GetMapping("/getBooksByAuthor/{author}")
    public Books getBooksByAuthor(@PathVariable("author") String author) {
        return bookstoreServices.getBooksByAuthor(author);
    }



    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book insertBook) {
        return bookstoreServices.addBook(insertBook);
    }


    @PostMapping("/retrieveBooksByIds")
    public Books retrieveBooksByIds(@RequestBody BookIds bookIds) {
        return bookstoreServices.retrieveBooksByIds(bookIds);
    }



    @GetMapping("/getCustomerByName/{customerName}")
    public Customer getCustomerByName(@PathVariable("customerName") String customerName) {
        return bookstoreServices.getCustomerByName(customerName);
    }


    @GetMapping("/getBookOrdersByCustomerName/{customerName}")
    public Books getBookOrdersByCustomerName(@PathVariable("customerName") String customerName) {
        return bookstoreServices.getBookOrdersByCustomerName(customerName);
    }



    @PostMapping("/addCustomer/{customerName}")
    ResponseEntity<String> addCustomer(@PathVariable("customerName") String customerName) {

        return bookstoreServices.addCustomer(customerName);
    }


    @PostMapping("/orderBook/{customerName}/{bookId}")
    ResponseEntity orderBook(@PathVariable("customerName") String customerName,
                             @PathVariable("bookId") Long bookId) {
        return bookstoreServices.orderBook(customerName,bookId);
    }


    @GetMapping("/getOrder/{customerName}/{orderId}")
    public OrderUIView getOrder(@PathVariable("customerName") String customerName,
                                @PathVariable("orderId") Long orderId){
        return new OrderUIView(bookstoreServices.getOrder(customerName,orderId));
    }


}
