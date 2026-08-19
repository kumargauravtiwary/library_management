package com.example.library;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Library {

    private final Map<String, Book> books = new HashMap<>();

    private final Map<String, Member> members = new HashMap<>();

    private final Map<String, Loan> activeLoans = new HashMap<>();

    private final Map<String, Queue<Reservation>> reservations =
            new HashMap<>();

    private final AtomicLong loanSequence = new AtomicLong(1);
    private final AtomicLong reservationSequence = new AtomicLong(1);

    private final FineCalculator fineCalculator;

    public Library(FineCalculator fineCalculator) {
        this.fineCalculator = fineCalculator;
    }

    // ----------------------------------------------------
    // BOOK MANAGEMENT
    // ----------------------------------------------------

    public synchronized void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public synchronized void addBookCopy(
            String isbn,
            BookCopy copy) {

        Book book = books.get(isbn);

        if (book == null) {
            throw new IllegalArgumentException(
                    "Book does not exist: " + isbn);
        }

        book.addCopy(copy);
    }

    // ----------------------------------------------------
    // MEMBER MANAGEMENT
    // ----------------------------------------------------

    public synchronized void registerMember(Member member) {

        members.put(
                member.getMemberId(),
                member);
    }

    // ----------------------------------------------------
    // SEARCH
    // ----------------------------------------------------

    public synchronized List<Book> searchByTitle(
            String title) {

        return books.values()
                .stream()
                .filter(book ->
                        book.getTitle()
                                .toLowerCase()
                                .contains(title.toLowerCase()))
                .toList();
    }

    public synchronized List<Book> searchByAuthor(
            String author) {

        return books.values()
                .stream()
                .filter(book ->
                        book.getAuthor()
                                .toLowerCase()
                                .contains(author.toLowerCase()))
                .toList();
    }

    public synchronized Book searchByISBN(String isbn) {
        return books.get(isbn);
    }

    // ----------------------------------------------------
    // BORROW
    // ----------------------------------------------------

    public synchronized Loan borrowBook(
        String memberId,
        String isbn) {

    Member member = members.get(memberId);

    if (member == null) {
        throw new IllegalArgumentException(
                "Member not found");
    }

    // Check borrowing limit first
    if (!member.canBorrow()) {
        throw new IllegalStateException(
                "Member has reached maximum borrowing limit of 5 books");
    }

    Book book = books.get(isbn);

    if (book == null) {
        throw new IllegalArgumentException(
                "Book not found");
    }

    BookCopy copy = book.getAvailableCopy();

    if (copy == null) {

        reserveBook(memberId, isbn);

        throw new IllegalStateException(
                "No copy available. Book reserved.");
    }

    copy.setStatus(BookStatus.BORROWED);

    String loanId =
            "LOAN-" + loanSequence.getAndIncrement();

    LocalDate issueDate = LocalDate.now();

    LocalDate dueDate =
            issueDate.plusDays(14);

    Loan loan = new Loan(
            loanId,
            copy,
            member,
            issueDate,
            dueDate);

    activeLoans.put(loanId, loan);

    member.addLoan(loan);

    return loan;
}

    // ----------------------------------------------------
    // RETURN
    // ----------------------------------------------------

    public synchronized double returnBook(
            String loanId) {

        Loan loan = activeLoans.get(loanId);

        if (loan == null) {
            throw new IllegalArgumentException(
                    "Loan not found");
        }

        if (loan.isReturned()) {
            throw new IllegalStateException(
                    "Book already returned");
        }

        loan.returnBook(LocalDate.now());

        BookCopy copy = loan.getBookCopy();

        copy.setStatus(BookStatus.AVAILABLE);

        double fine =
                fineCalculator.calculate(
                        loan.getOverdueDays());

        activeLoans.remove(loanId);

        processNextReservation(copy.getBook());

        return fine;
    }

    // ----------------------------------------------------
    // RESERVATION
    // ----------------------------------------------------

    public synchronized void reserveBook(
            String memberId,
            String isbn) {

        Member member = members.get(memberId);

        Book book = books.get(isbn);

        if (member == null) {
            throw new IllegalArgumentException(
                    "Member not found");
        }

        if (book == null) {
            throw new IllegalArgumentException(
                    "Book not found");
        }

        String reservationId =
                "RES-" +
                reservationSequence.getAndIncrement();

        Reservation reservation =
                new Reservation(
                        reservationId,
                        book,
                        member);

        reservations
                .computeIfAbsent(
                        isbn,
                        k -> new LinkedList<>())
                .offer(reservation);
    }

    private void processNextReservation(Book book) {

        Queue<Reservation> queue =
                reservations.get(book.getIsbn());

        if (queue == null || queue.isEmpty()) {
            return;
        }

        Reservation reservation =
                queue.poll();

        System.out.println(
                "Book available for member: "
                        + reservation
                        .getMember()
                        .getName());
    }
}