package com.example.library;

public class EBook extends LibraryItem {

    private final String isbn;
    private final String downloadUrl;
    private final String format;
    private final double fileSizeMB;

    public EBook(
            String isbn,
            String title,
            String author,
            String downloadUrl,
            String format,
            double fileSizeMB) {

        super(isbn, title, author);

        this.isbn = isbn;
        this.downloadUrl = downloadUrl;
        this.format = format;
        this.fileSizeMB = fileSizeMB;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getFormat() {
        return format;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }
}
