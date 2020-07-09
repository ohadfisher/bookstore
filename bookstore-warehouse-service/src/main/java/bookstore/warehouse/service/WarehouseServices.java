package bookstore.warehouse.service;


import bookstore.warehouse.model.Book;
import bookstore.warehouse.model.BookIds;
import bookstore.warehouse.model.Books;
import bookstore.warehouse.repository.WarehouseBookCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WarehouseServices {

    private WarehouseBookCatalog warehouseBookCatalog;

    @Autowired
    public WarehouseServices(WarehouseBookCatalog warehouseBookCatalog) {
        this.warehouseBookCatalog =warehouseBookCatalog;
    }



    public boolean addBook(Book book) {
        if (! isBookExistById(book.getId())) {
            return warehouseBookCatalog.addBook(book);
        }
        return false;
    }


    public void removeBookById(Long bookId) {
        if (isBookExistById(bookId)) {
            Book book = warehouseBookCatalog.getBookById(bookId);
            warehouseBookCatalog.removeBook(book);
        }
    }


    public Optional<Book> getBookById(Long bookId){
        if(isBookExistById(bookId)) {
            return Optional.of(warehouseBookCatalog.getBookById(bookId));
        }
        return Optional.empty();
    }

    public Optional<Books> getBooksByBookName(String bookName){
        if(isBookExistByName(bookName)) {
            return Optional.of(warehouseBookCatalog.getBooksByBookName(bookName));
        }
        return Optional.empty();
    }

    public Optional<Books> getBooksByAuthor(String AuthorName){
        if(isBookExistByAuthor(AuthorName)) {
            return Optional.of(warehouseBookCatalog.getBooksByAuthor(AuthorName));
        }
        return Optional.empty();
    }




 public Optional<Books> getBooksById(BookIds bookIds) {
     Set<Book> bookList =
             bookIds.getBookIds().stream()
                     .filter(this::isBookExistById)
                     .map(bookId -> getBookById(bookId).get())
                     .collect(Collectors.toSet());

     if (bookList != null && ! bookList.isEmpty()) {
         return Optional.of(new Books(bookList));
     }
     return Optional.empty();
 }



    public boolean isBookExistById(Long bookId) {
        return warehouseBookCatalog.isBookExistById(bookId);
    }

    private boolean isBookExistByName(String bookName) {
        return warehouseBookCatalog.isBookExistByName(bookName);
    }

    private boolean isBookExistByAuthor(String bookAuthor) {
        try {
            return warehouseBookCatalog.isBookExistByAuthor(bookAuthor);
        }catch (Exception e){
            return false;
        }
    }
}
