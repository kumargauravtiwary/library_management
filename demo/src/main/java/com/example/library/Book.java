package com.example.library;
import java.util.ArrayList;
import java.util.List;

public class Book extends LibraryItem {

    private final String isbn;
    
    private final List<BookCopy> copies =
            new ArrayList<>();

    public Book(
            String isbn,
            String title,
            String author) {

        super(isbn, title, author);

        this.isbn = isbn;
    }

    public void addCopy(BookCopy copy) {
        copies.add(copy);
    }

    public String getIsbn() {
        return isbn;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public BookCopy getAvailableCopy() {

        return copies.stream()
                .filter(copy ->
                        copy.getStatus()
                                == BookStatus.AVAILABLE)
                .findFirst()
                .orElse(null);
    }
}