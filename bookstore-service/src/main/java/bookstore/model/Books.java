package bookstore.model;

import java.util.HashSet;
import java.util.Set;

public class Books {

    private Set<Book> bookSet = new HashSet<>();

    public Books() {
    }

    public Books(Set<Book> bookSet) {
        if (bookSet != null) {
            this.bookSet = bookSet;
        }
    }

    public Set<Book> getBookSet() {
        return bookSet;
    }

    public boolean addBook(Book book) {
        return bookSet.add(book);
    }

    public boolean removeBook(Book book) {
        return bookSet.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Books books = (Books) o;

        return bookSet.equals(books.bookSet);
    }

    @Override
    public int hashCode() {
        return bookSet.hashCode();
    }
}
