package bookstore.warehouse.model;

import java.util.HashSet;
import java.util.Set;

public class BookIds {
    private Set<Long> bookIds = new HashSet<>();


    public BookIds() {
    }

    public BookIds(Set<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public boolean addId(Long id) {
        return bookIds.add(id);
    }

    public Set<Long> getBookIds() {
        return bookIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookIds bookIds1 = (BookIds) o;

        return bookIds.equals(bookIds1.bookIds);
    }

    @Override
    public int hashCode() {
        return bookIds.hashCode();
    }
}
