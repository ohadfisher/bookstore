package bookstore.crm.service;

import bookstore.crm.model.*;
import bookstore.crm.repository.CrmCustomers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CrmServicesTest {

    @Mock
    private CrmCustomers crmCustomers;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @InjectMocks
    private CrmServices crmServices;

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
    private String customerName = "Ohad1";

    LocalDate date = LocalDate.now();

    private Customer customer_order1_2;
    private Customer customer_order1_2_3_4;
    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;

    private Set setOfId;
    private BookIds bookIds_3_4;
    private BookIds bookIds_1_2_3_4;


    private Books books1_2_3_4;



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


        setOfId = new HashSet();
        setOfId.add(bookAsReturnValue3.getId());
        setOfId.add(bookAsReturnValue4.getId());

        bookIds_3_4 = new BookIds(setOfId);

        setOfId.add(bookAsReturnValue1.getId());
        setOfId.add(bookAsReturnValue2.getId());

        bookIds_1_2_3_4 = new BookIds(setOfId);

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(bookAsReturnValue1);
        bookSet.add(bookAsReturnValue2);
        bookSet.add(bookAsReturnValue3);
        bookSet.add(bookAsReturnValue4);

        books1_2_3_4 = new Books(bookSet);

         order1 = new Order(customerName,bookAsReturnValue1);
         order2= new Order(customerName,bookAsReturnValue2);
         order3= new Order(customerName,bookAsReturnValue3);
         order4= new Order(customerName,bookAsReturnValue4);

        Set<Order> orderSet =new HashSet();
        orderSet.add(order1);
        orderSet.add(order2);


        customer_order1_2 = new Customer(customerName,orderSet);

        orderSet.add(order3);
        orderSet.add(order4);
        customer_order1_2_3_4 = new Customer(customerName,orderSet);

    }



    /*
    expectedEx.expect(NoSuchElementException.class);
    expectedEx.expectMessage("No value present");

    Book result = bookstoreServices.getBookById(1);
        Assert.assertEquals(bookAsReturnValue1,result);
    }

        */

    @Test
    public void getCustomer() {
        when(crmCustomers.isCustomerExist(customerName)).thenReturn(true);
        when(crmCustomers.getCustomer(customerName)).thenReturn(customer_order1_2_3_4);
        Optional<Customer> result = crmServices.getCustomer(customerName);
        Assert.assertEquals(Optional.of(customer_order1_2_3_4),result);

    }
@Test
    public void getCustomerNotExist() {
        when(crmCustomers.isCustomerExist(customerName)).thenReturn(false);
        Optional<Customer> result = crmServices.getCustomer(customerName);
        Assert.assertEquals(Optional.empty(),result);
    }






 @Test
    public void addConsumer() {
        String customerName = "CN";
        Customer customerEmpty = new Customer(customerName);

        when(crmCustomers.isCustomerExist(customerName)).thenReturn(false);
        when(crmCustomers.addCustomer(customerEmpty)).thenReturn(true);
        Assert.assertTrue(crmServices.addConsumer(customerName));
    }

    @Test
    public void addConsumerNotValidNameEmpty() {
        String customerName = "";
        Assert.assertFalse(crmServices.addConsumer(customerName));
    }

    @Test
    public void addConsumerNotValidNameNull() {
        String customerName = null;
        Assert.assertFalse(crmServices.addConsumer(customerName));
    }
    @Test
    public void addConsumerNotValidNameAlreadyExist() {
        String customerName = "CN";
        when(crmCustomers.isCustomerExist(customerName)).thenReturn(true);
        Assert.assertFalse(crmServices.addConsumer(customerName));
    }




    @Test
    public void addOrderToCustomer() {
        when(crmCustomers.isCustomerExist(customerName)).thenReturn(true);
        when(crmCustomers.getCustomer(customerName)).thenReturn(customer_order1_2_3_4);

        Optional<Long> result = crmServices.addOrderToCustomer(customerName,bookAsReturnValue3);
        Assert.assertTrue(result.get().equals(4L));
    }
    @Test
    public void addOrderToCustomerCustomerNotExist() {
        when(crmCustomers.isCustomerExist(customerName)).thenReturn(false);
        Optional<Long> result = crmServices.addOrderToCustomer(customerName,bookAsReturnValue3);
        Assert.assertEquals(Optional.empty(),result);
    }


}