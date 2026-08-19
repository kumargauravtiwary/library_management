package com.example.library;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private final String loanId;
    private final BookCopy bookCopy;
    private final Member member;

    private final LocalDate issueDate;
    private final LocalDate dueDate;

    private LocalDate returnDate;

    public Loan(
            String loanId,
            BookCopy bookCopy,
            Member member,
            LocalDate issueDate,
            LocalDate dueDate) {

        this.loanId = loanId;
        this.bookCopy = bookCopy;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public void returnBook(LocalDate date) {
        this.returnDate = date;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public long getOverdueDays() {

        LocalDate endDate =
                returnDate != null
                        ? returnDate
                        : LocalDate.now();

        if (!endDate.isAfter(dueDate)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(dueDate, endDate);
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public Member getMember() {
        return member;
    }
}