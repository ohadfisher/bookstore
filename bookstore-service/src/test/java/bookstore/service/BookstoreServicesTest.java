package bookstore.service;

import bookstore.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class BookstoreServicesTest {



    @Mock
    private RestTemplate restTemplate;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @InjectMocks
    private BookstoreServices bookstoreServices;

    private String WAREHOUSE_URL = bookstoreServices.WAREHOUSE_URL;
    private String CRM_URL = bookstoreServices.CRM_URL;



    private Long bookId1;
    private String bookName1;
    private String bookAuthor1;
    private Book bookAsReturnValue1;

    private Long bookId2;
    private String bookAuthor2;

    private Book bookAsReturnValue2;
    private Long bookId3;
    private String bookName3;
    private String bookAuthor3;
    private Book bookAsReturnValue3;

    private Long bookId4;
    private String bookName4;

    private Book bookAsReturnValue4;
    private String customerName = "Ohad";
    private LocalDate date = LocalDate.now();

    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;

    private Set<Order> orders;
    private Customer customer;

    private Set setOfId;
    private BookIds bookIds_3_4;
    private BookIds bookIds_1_2_3_4;

    private Books books;


    @Before
    public void setUp() {
        bookId1 = 1L;
        bookName1 = "N_KI1";
        bookAuthor1 = "A_KI1";
        bookAsReturnValue1 = new Book(bookId1,bookName1,bookAuthor1);
        bookId2 = 2L;
        bookAuthor2 = "A_KI2";
        bookAsReturnValue2 = new Book(bookId2,bookName1,bookAuthor2);
        bookId3 = 3L;
        bookName3 = "N_KI33";
        bookAuthor3 = "A_33";
        bookAsReturnValue3 = new Book(bookId3,bookName3,bookAuthor3);
        bookId4 = 4L;
        bookName4 = "N_KI44";
        bookAsReturnValue4 = new Book(bookId4,bookName4,bookAuthor3);

        order1 = new Order(1L,customerName,bookAsReturnValue1,date);
        order2 = new Order(2L,customerName,bookAsReturnValue2,date);
        order3 = new Order(3L,customerName,bookAsReturnValue3,date);
        order4 = new Order(4L,customerName,bookAsReturnValue4,date);

        orders = new HashSet();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        customer = new Customer(customerName,orders);

        setOfId = new HashSet();
        setOfId.add(order3.getBookDetails().getId());
        setOfId.add(order4.getBookDetails().getId());

        bookIds_3_4 = new BookIds(setOfId);

        setOfId.add(order1.getBookDetails().getId());
        setOfId.add(order2.getBookDetails().getId());


        bookIds_1_2_3_4 = new BookIds(setOfId);
        books = new Books();


    }

    //===getBookById==

    @Test/*(expected = NoSuchElementException.class)*/
    public void testGetBookByIdNotExist() {
        expectedEx.expect(NoSuchElementException.class);
        expectedEx.expectMessage("No value present");

        when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBookById/" + 1,Optional.class)).thenReturn(Optional.empty());
        bookstoreServices.getBookById(1);
    }
    @Test
    public void testGetBookById() {
        when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBookById/" + 1,Optional.class)).thenReturn(Optional.of(bookAsReturnValue1));
        //when(objectMapper.convertValue(any(Book.class),Book.class)).thenReturn(bookAsReturnValue);
        Book result = bookstoreServices.getBookById(1);
        Assert.assertEquals(bookAsReturnValue1,result);
    }




    //===getBooksByName==
    @Test
    public void getBooksByName() {
        Books books = new Books();
        books.addBook(bookAsReturnValue1);
        books.addBook(bookAsReturnValue2);
        when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBooksByName/" + bookName1,Optional.class)).thenReturn(Optional.of(books));
        Books result = bookstoreServices.getBooksByName(bookName1);
        Assert.assertEquals(books,result);

    }

    @Test
    public void getBooksByNameEmpty() {
       expectedEx.expect(NoSuchElementException.class);
       expectedEx.expectMessage("No value present");

       String name = "notExistName";
       when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBooksByName/" + name,Optional.class)).thenReturn(Optional.empty());
       bookstoreServices.getBooksByName(name);
    }


    //===getBooksByAuthor==
    @Test
    public void getBooksByAuthor() {
        Books books = new Books();
        books.addBook(bookAsReturnValue3);
        books.addBook(bookAsReturnValue4);
        when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBooksByAuthor/" + bookName3,Optional.class)).thenReturn(Optional.of(books));
        Books result = bookstoreServices.getBooksByAuthor(bookName3);
        Assert.assertEquals(books,result);
    }

    @Test
    public void getBooksByAuthorEmpty() {
       expectedEx.expect(NoSuchElementException.class);
       expectedEx.expectMessage("No value present");

       String authorName = "notExistName";
       when(restTemplate.getForObject(WAREHOUSE_URL+"warehouse/getBooksByAuthor/" + authorName,Optional.class)).thenReturn(Optional.empty());
       bookstoreServices.getBooksByAuthor(authorName);
    }




    //===addBook==
    @Test
    public void addBook() {
       when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/addBook",bookAsReturnValue1,Boolean.class)).thenReturn(true);
        ResponseEntity<String> result = bookstoreServices.addBook(bookAsReturnValue1);
        Assert.assertEquals(new ResponseEntity<>("Book add successfully", HttpStatus.CREATED),result);
    }

    @Test
    public void addBookNotSuccess() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Book id is already exist");

        when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/addBook",bookAsReturnValue1,Boolean.class)).thenReturn(false);
        bookstoreServices.addBook(bookAsReturnValue1);
    }

    //===retrieveBooksByIds==
    @Test
    public void retrieveBooksByIds() {
        books.addBook(bookAsReturnValue3);
        books.addBook(bookAsReturnValue4);

        when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/booksByIds", bookIds_3_4,Optional.class)).thenReturn(Optional.of(books));
        Books result = bookstoreServices.retrieveBooksByIds(bookIds_3_4);
        Assert.assertEquals(books,result);
    }
 @Test
    public void retrieveBooksByIds2() {
     books.addBook(bookAsReturnValue1);
     books.addBook(bookAsReturnValue2);
     books.addBook(bookAsReturnValue3);
     books.addBook(bookAsReturnValue4);

     when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/booksByIds", bookIds_1_2_3_4,Optional.class))
             .thenReturn(Optional.of(books));


        Books result = bookstoreServices.retrieveBooksByIds(bookIds_1_2_3_4);
        Assert.assertEquals(books,result);
    }

    @Test
    public void retrieveBooksByIdsEmpty() {
        expectedEx.expect(NoSuchElementException.class);
        expectedEx.expectMessage("No value present");


        Set setOfId = new HashSet();
        setOfId.add(33);
        setOfId.add(44);

        BookIds bookIds = new BookIds(setOfId);

        Books books = new Books();
        books.addBook(bookAsReturnValue3);
        books.addBook(bookAsReturnValue4);

        when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/booksByIds",bookIds,Optional.class)).thenReturn(Optional.empty());
        bookstoreServices.retrieveBooksByIds(bookIds);
    }



    //===getCustomerByName==
    @Test
    public void getCustomerByName() {
        when(restTemplate.getForObject(
                CRM_URL+"crm/getCustomerByName/" + customerName, Optional.class))
                .thenReturn(Optional.of(customer));
        Customer result = bookstoreServices.getCustomerByName(customerName);
        Assert.assertEquals(customer,result);
    }

    @Test
    public void getCustomerByNameIsEmpty() {
        expectedEx.expect(NoSuchElementException.class);
        expectedEx.expectMessage("No value present");

        String customerName = "OhadCustomer";
        when(restTemplate.getForObject(
                CRM_URL+"crm/getCustomerByName/" + customerName, Optional.class))
                .thenReturn(Optional.empty());
        bookstoreServices.getCustomerByName(customerName);
    }

    //===getBookOrdersByCustomerName==
    @Test
    public void getBookOrdersByCustomerName() {
        books.addBook(bookAsReturnValue1);
        books.addBook(bookAsReturnValue2);
        books.addBook(bookAsReturnValue3);
        books.addBook(bookAsReturnValue4);

        when(restTemplate.getForObject(
                CRM_URL+"crm/getCustomerByName/" + customerName, Optional.class))
                .thenReturn(Optional.of(customer));

        when(restTemplate.postForObject(WAREHOUSE_URL+"warehouse/booksByIds", bookIds_1_2_3_4,Optional.class))
                .thenReturn(Optional.of(books));

        Books result = bookstoreServices.getBookOrdersByCustomerName(customerName);

        Assert.assertEquals(books,result);


    }
   /*
    @Test
    public void getBookOrdersByCustomerName() {


    }
*/
    //===addCustomer==
    @Test
    public void addCustomer() {
        when(restTemplate.postForObject(CRM_URL+"crm/addCustomer", customerName, Boolean.class))
                .thenReturn(true);

        ResponseEntity<String> result = bookstoreServices.addCustomer(customerName);

        ResponseEntity<String> expected = new ResponseEntity<>("Customer Name: "+customerName+" created", HttpStatus.CREATED);
        Assert.assertEquals(expected,result);

    }

    @Test
    public void addCustomerEmpty() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Customer Name: "+customerName+" ,is already exist");

        when(restTemplate.postForObject(CRM_URL+"crm/addCustomer", customerName, Boolean.class))
                .thenReturn(false);

        bookstoreServices.addCustomer(customerName);
    }




    //===orderBook==
    @Test
    public void orderBook() {
        Long bookId = bookAsReturnValue1.getId();

        when(restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBookById/" + bookId, Optional.class))
                .thenReturn(Optional.of(bookAsReturnValue1));

        when(restTemplate.postForObject
                (CRM_URL+"crm/addOrderToCustomer/" + customerName, bookAsReturnValue1, Optional.class))
                .thenReturn(Optional.of(1L));

        ResponseEntity<String> result = bookstoreServices.orderBook(customer.getName(),bookId);
        ResponseEntity<String> expected = new ResponseEntity<>
                ("Customer: " + customerName + ", was add order number  "
                        + 1 + " with bookId: " + bookId, HttpStatus.OK);

        Assert.assertEquals(expected,result);

    }

    @Test
    public void orderBookEmpty() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Customer: "+ customerName+", was ***not** add order of book id 5, because the book not exist at the warehouse!");
        Long bookId = 5L;

        when(restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBookById/" + bookId, Optional.class))
                .thenReturn(Optional.empty());

        bookstoreServices.orderBook(customer.getName(),bookId);

    }

 @Test
    public void orderBookEmpty2() {
        Long bookId = bookAsReturnValue4.getId();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("OrderBook method, failed create order of book id "+bookAsReturnValue4.getId()+ " to customer: "+customerName);

        when(restTemplate.getForObject(
                WAREHOUSE_URL+"warehouse/getBookById/" + bookId, Optional.class))
                .thenReturn(Optional.of(bookAsReturnValue4));

     when(restTemplate.postForObject
             (CRM_URL+"crm/addOrderToCustomer/" + customerName, bookAsReturnValue4, Optional.class))
             .thenReturn(Optional.empty());


        bookstoreServices.orderBook(customer.getName(),bookId);

    }

    //===getOrder==
   /* @Test
    public void getOrder() {

    }
*/

    @Test
    public void getOrder() {

        when(restTemplate.getForObject(CRM_URL+"crm/getOrder/{customerName}/{orderId}"
                , Optional.class, customerName, order1.getOrderId())).thenReturn(Optional.of(order1));


        Order result = bookstoreServices.getOrder(customerName,order1.getOrderId());


        Assert.assertEquals(order1,result);

    }

    @Test
    public void getOrderEmpty() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("getOrder method, failed get order id: "+order1.getOrderId()+" from customer: "+customerName);

        when(restTemplate.getForObject(CRM_URL+"crm/getOrder/{customerName}/{orderId}"
                , Optional.class, customerName, order1.getOrderId())).thenReturn(Optional.empty());


       bookstoreServices.getOrder(customerName,order1.getOrderId());




    }

}
