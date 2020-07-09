package bookstore.service;

import bookstore.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookstoreServices {

    private static Logger log = Logger.getLogger(BookstoreServices.class);

    public final static String  WAREHOUSE_URL = "http://bookstore-warehouse-service:8082/";
    public final static String  CRM_URL = "http://bookstore-crm-service:8083/";
    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    @Autowired
    public BookstoreServices(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
    }


    public Book getBookById(long id) {
        Optional<Book> optionalBook = (restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBookById/" + id, Optional.class));
        return mapper.convertValue(optionalBook.get(), Book.class);
    }

    public Books getBooksByName(String name) {
        Optional<Books> optionalBooks = restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBooksByName/" + name, Optional.class);
        return mapper.convertValue(optionalBooks.get(), Books.class);
    }

    public Books getBooksByAuthor(String author) {
        Optional<Books> optionalBooks = restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBooksByAuthor/" + author, Optional.class);
        return mapper.convertValue(optionalBooks.get(), Books.class);
    }



    public ResponseEntity<String> addBook(Book insertBook) {
        Boolean isBookAdd = restTemplate.postForObject(
                WAREHOUSE_URL+"warehouse/addBook", insertBook, Boolean.class);
        if(isBookAdd){
            return new ResponseEntity<>("Book add successfully", HttpStatus.CREATED);
        }else{
            throw new IllegalArgumentException("Book id is already exist");
        }
    }


    public Books retrieveBooksByIds(BookIds bookIds) {
        Optional<Books> optionalBooks =  restTemplate.postForObject(
                WAREHOUSE_URL+"warehouse/booksByIds", bookIds, Optional.class);
        return mapper.convertValue(optionalBooks.get(), Books.class);
    }

/////////////////////////


    public Customer getCustomerByName(String customerName) {
        Optional<Customer> optionalCustomer = restTemplate.getForObject(
                CRM_URL+"crm/getCustomerByName/" + customerName, Optional.class);
        return mapper.convertValue(optionalCustomer.get(), Customer.class);
    }


    // get all books that was order and erase all the order(because supplied)
    public Books getBookOrdersByCustomerName(String customerName) {
        Customer  customer = getCustomerByName(customerName);
        if(customer.getOrders().isEmpty()){
            return new Books();
        }
        BookIds bookIds = new BookIds(customer.getOrders().stream().map(order -> order.getBookDetails().getId()).collect(Collectors.toSet()));
        Books books = retrieveBooksByIds(bookIds);

        eraseCustomerOrders(customerName);
        return books;
    }

    private void eraseCustomerOrders(String customerName) {
        restTemplate.put(CRM_URL+"crm/removeAllOrdersFromCustomer", customerName);
    }


    public ResponseEntity<String> addCustomer(String customerName) {
        Boolean isCustomerAdded  = restTemplate.postForObject(
                CRM_URL+"crm/addCustomer", customerName, Boolean.class);
        if(isCustomerAdded) {
            return new ResponseEntity<>("Customer Name: "+customerName+" created", HttpStatus.CREATED);
        }else {
            throw new IllegalArgumentException("Customer Name: "+customerName+" ,is already exist");
        }
    }


    public ResponseEntity orderBook( String customerName, Long bookId) {

        Optional<Book> optionalBook = restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBookById/" + bookId, Optional.class);
        if (optionalBook.isPresent()) {
            Optional<Long> optionalOrderId = restTemplate.postForObject
                    (CRM_URL+"crm/addOrderToCustomer/" + customerName, optionalBook.get(), Optional.class);

            if(optionalOrderId.isPresent()){
                return new ResponseEntity<>
                        ("Customer: " + customerName + ", was add order number  "
                                + optionalOrderId.get() + " with bookId: " + bookId, HttpStatus.OK);
            }else{
                String message = "OrderBook method, failed create order of book id "+bookId+" to customer: "+customerName;
                log.error(message);
                throw new IllegalArgumentException(message);
                //return new ResponseEntity(message, HttpStatus.UNAUTHORIZED);
            }

        } else {
            String message = "Customer: " + customerName + ", was ***not** add order of book id "
                    + bookId + ", because the book not exist at the warehouse!";
            log.error(message);
            throw new IllegalArgumentException(message);

        }
    }


    public Order getOrder(String customerName, Long orderId){

        Optional<Order> optionalOrder =
                restTemplate.getForObject(CRM_URL+"crm/getOrder/{customerName}/{orderId}"
                        , Optional.class, customerName, orderId);

        if (optionalOrder.isPresent()){
            return mapper.convertValue(optionalOrder.get(), Order.class);
        }else{
            String message = "getOrder method, failed get order id: "+orderId+" from customer: "+customerName;
            log.error(message);
            throw new IllegalArgumentException(message);
        }

    }


}
