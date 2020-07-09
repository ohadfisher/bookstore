package bookstore.warehouse.model;

public class Book {
    private long id;
    private String name;
    private String author;

    public Book() {
    }

    public Book(long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public static class BookBuilder {
        private long id;
        private String name;
        private String author;

        public BookBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public BookBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Book build() {
            return new Book(id, name, author);
        }
    }

}
