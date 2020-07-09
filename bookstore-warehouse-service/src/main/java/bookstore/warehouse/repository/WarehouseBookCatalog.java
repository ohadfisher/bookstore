package bookstore.warehouse.repository;

import bookstore.warehouse.model.Book;
import bookstore.warehouse.model.Books;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WarehouseBookCatalog {
    static Logger log = Logger.getLogger(WarehouseBookCatalog.class.getName());


    private Map<String, Books> catalogMapByBookName = new HashMap<>();
    private Map<String, Books> catalogMapByAuthor = new HashMap<>();
    private Map<Long, Book> catalogMapByBookId = new HashMap<>();




    public boolean addBook(Book book) {
        //Move to service
        if (! isBookExistById(book.getId())) {

            if (!catalogMapByBookName.containsKey(book.getName())) {
                addEmptyEntityToStringBooksMap(catalogMapByBookName,book.getName());
            }
            if (!catalogMapByAuthor.containsKey(book.getAuthor())) {
                addEmptyEntityToStringBooksMap(catalogMapByAuthor,book.getAuthor());
            }

            catalogMapByBookId.put(book.getId(), book);
            catalogMapByBookName.get(book.getName()).addBook(book);
            catalogMapByAuthor.get(book.getAuthor()).addBook(book);

            return true;
        }
        return false;
    }

    public void removeBook(Book book) {
            catalogMapByAuthor.get(book.getAuthor()).removeBook(book);
            catalogMapByBookName.get(book.getName()).removeBook(book);
            catalogMapByBookId.remove(book.getId());
    }


    //
    public Book getBookById(Long bookId){
        return catalogMapByBookId.get(bookId);
    }

    public Books getBooksByBookName(String bookName){
            return catalogMapByBookName.get(bookName);
    }

    public Books getBooksByAuthor(String AuthorName){
            return catalogMapByAuthor.get(AuthorName);
    }



    //
    public boolean isBookExistById(Long bookId){
        return catalogMapByBookId.containsKey(bookId);
    }
    public boolean isBookExistByName(String bookName) {
        return catalogMapByBookName.containsKey(bookName);
    }
    public boolean isBookExistByAuthor(String bookAuthor) {
        return catalogMapByAuthor.containsKey(bookAuthor);
    }

    private boolean addEmptyEntityToStringBooksMap(Map<String, Books> mapOfBooks,String key){
        try{
            mapOfBooks.put(key, new Books());
        }catch (Exception e){
            return false;
        }
        return true;
    }


}
