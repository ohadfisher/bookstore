package bookstore.warehouse.controller;


import bookstore.warehouse.model.Book;
import bookstore.warehouse.model.BookIds;
import bookstore.warehouse.model.Books;
import bookstore.warehouse.service.WarehouseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/warehouse/")
public class WarehouseController {

    private WarehouseServices warehouseServices;

    @Autowired
    public WarehouseController(WarehouseServices warehouseServices) {
        this.warehouseServices = warehouseServices;
    }


    @PostMapping("/addBook")
    boolean addBook(@RequestBody Book book) {
       return warehouseServices.addBook(book);

    }

    @GetMapping("/isBookExistById/{bookId}")
    public Boolean isBookExist(@PathVariable("bookId") long bookId){
        return warehouseServices.isBookExistById(bookId);
    }

    @GetMapping("/getBookById/{bookId}")
    public Optional<Book> getBookById(@PathVariable("bookId") long bookId){
        return warehouseServices.getBookById(bookId);
    }

    @PostMapping("/booksByIds")
    public Optional<Books> bookByIds(@RequestBody BookIds bookIds) {
        return warehouseServices.getBooksById(bookIds);
    }

    @GetMapping("/getBooksByName/{bookName}")
    public Optional<Books> getBooksByName(@PathVariable("bookName") String bookName){
      return warehouseServices.getBooksByBookName(bookName);
    }

    @GetMapping("/getBooksByAuthor/{authorName}")
    public Optional<Books> getBooksByAuthor(@PathVariable("authorName") String authorName){
        return warehouseServices.getBooksByAuthor(authorName);
    }

}
