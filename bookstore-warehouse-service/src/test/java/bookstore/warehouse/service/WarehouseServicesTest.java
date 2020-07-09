package bookstore.warehouse.service;

import bookstore.warehouse.model.Book;
import bookstore.warehouse.model.BookIds;
import bookstore.warehouse.model.Books;
import bookstore.warehouse.repository.WarehouseBookCatalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseServicesTest {

    @Mock
    private WarehouseBookCatalog warehouseBookCatalog;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @InjectMocks
    private WarehouseServices warehouseServices;

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
    String customerName = "Ohad";
    LocalDate date = LocalDate.now();



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


    }


    @Test
    public void addBook() {
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(false);
        when(warehouseBookCatalog.addBook(bookAsReturnValue1)).thenReturn(true);
        Boolean result = warehouseServices.addBook(bookAsReturnValue1);
        Assert.assertEquals(true,result);

    }

    @Test
    public void addBookButBookIdIsTaken() {
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(true);
        Boolean result = warehouseServices.addBook(bookAsReturnValue1);
        Assert.assertEquals(false,result);
    }

    @Test
    public void removeBookById() {
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(true);
        when(warehouseBookCatalog.getBookById(bookAsReturnValue1.getId())).thenReturn(bookAsReturnValue1);
        warehouseServices.removeBookById(bookAsReturnValue1.getId());
        verify(warehouseBookCatalog, times(1)).isBookExistById(bookAsReturnValue1.getId());
        verify(warehouseBookCatalog, times(1)).getBookById(bookAsReturnValue1.getId());
    }


    @Test
    public void removeBookByIdThatNotExist() {
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(false);
        warehouseServices.removeBookById(bookAsReturnValue1.getId());
        verify(warehouseBookCatalog, times(1)).isBookExistById(bookAsReturnValue1.getId());
        verify(warehouseBookCatalog, times(0)).getBookById(bookAsReturnValue1.getId());
    }

    @Test
    public void getBookById() {
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(true);
        when(warehouseBookCatalog.getBookById(bookAsReturnValue1.getId())).thenReturn(bookAsReturnValue1);
        Optional<Book> result = warehouseServices.getBookById(bookAsReturnValue1.getId());
        Assert.assertEquals(Optional.of(bookAsReturnValue1),result);

    }

    @Test
    public void getBookByIdThatNotExist() {

        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(false);
      //  when(warehouseBookCatalog.getBookById(bookAsReturnValue1.getId())).thenReturn(bookAsReturnValue1);
        Optional<Book> result = warehouseServices.getBookById(bookAsReturnValue1.getId());
        Assert.assertEquals(Optional.empty(),result);
    }

    @Test
    public void getBooksByBookName() {
        Set<Book> bookSetForTest = new HashSet<>();
        bookSetForTest.add(bookAsReturnValue1);
        bookSetForTest.add(bookAsReturnValue2);
        Books books = new Books(bookSetForTest);
        when(warehouseBookCatalog.isBookExistByName(bookAsReturnValue1.getName())).thenReturn(true);
        when(warehouseBookCatalog.getBooksByBookName(bookAsReturnValue1.getName())).thenReturn(books);

        Optional<Books> result = warehouseServices.getBooksByBookName(bookAsReturnValue1.getName());
        Assert.assertEquals(Optional.of(books),result);

    }

    @Test
    public void getBooksByBookNameThatNotExist() {
        when(warehouseBookCatalog.isBookExistByName(bookAsReturnValue1.getName())).thenReturn(false);
        Optional<Books> result = warehouseServices.getBooksByBookName(bookAsReturnValue1.getName());
        Assert.assertEquals(Optional.empty(),result);
    }

    @Test
    public void getBooksByAuthor() {
        Set<Book> bookSetForTest = new HashSet<>();
        bookSetForTest.add(bookAsReturnValue1);
        bookSetForTest.add(bookAsReturnValue2);
        Books books = new Books(bookSetForTest);

        when(warehouseBookCatalog.isBookExistByAuthor(bookAsReturnValue1.getAuthor())).thenReturn(true);
        when(warehouseBookCatalog.getBooksByAuthor(bookAsReturnValue1.getAuthor())).thenReturn(books);

        Optional<Books> result = warehouseServices.getBooksByAuthor(bookAsReturnValue1.getAuthor());
        Assert.assertEquals(Optional.of(books),result);
    }

    @Test
    public void getBooksByAuthorThatNotExist() {

        when(warehouseBookCatalog.isBookExistByAuthor(bookAsReturnValue1.getAuthor())).thenReturn(false);
        Optional<Books> result = warehouseServices.getBooksByAuthor(bookAsReturnValue1.getAuthor());
        Assert.assertEquals(Optional.empty(),result);
    }

    @Test
    public void getBooksById() {
        Set<Long> idsSet = new HashSet<>();
        idsSet.add(1L);
        idsSet.add(2L);
        BookIds bookIds = new BookIds(idsSet);

        Books books = new Books();
        books.addBook(bookAsReturnValue1);
        books.addBook(bookAsReturnValue2);

        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue1.getId())).thenReturn(true);
        when(warehouseBookCatalog.isBookExistById(bookAsReturnValue2.getId())).thenReturn(true);
        when(warehouseBookCatalog.getBookById(bookAsReturnValue1.getId())).thenReturn(bookAsReturnValue1);
        when(warehouseBookCatalog.getBookById(bookAsReturnValue2.getId())).thenReturn(bookAsReturnValue2);


        Optional<Books> result = warehouseServices.getBooksById(bookIds);
        Assert.assertEquals(Optional.of(books),result);
    }


    @Test
    public void getBooksByIdEmptyBooksIds() {
        BookIds bookIds = new BookIds();

        Optional<Books> result = warehouseServices.getBooksById(bookIds);
        Assert.assertEquals(Optional.empty(),result);    }


    @Test
    public void isBookExistByIdNotExist() {
        Assert.assertFalse(warehouseServices.isBookExistById(6L));
    }






}