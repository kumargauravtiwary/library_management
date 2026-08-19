package com.example.library;
public class Main {

    public static void main(String[] args) {

        Library library =
                new Library(
                        new DefaultFineCalculator());

        // -------------------------------
        // Create Book
        // -------------------------------

        Book cleanCode =
                new Book(
                        "9780132350884",
                        "Clean Code",
                        "Robert C. Martin");

        library.addBook(cleanCode);

        // -------------------------------
        // Add Copies
        // -------------------------------

        BookCopy copy1 =
                new BookCopy(
                        "COPY-001",
                        cleanCode);

        BookCopy copy2 =
                new BookCopy(
                        "COPY-002",
                        cleanCode);

        library.addBookCopy(
                cleanCode.getIsbn(),
                copy1);

        library.addBookCopy(
                cleanCode.getIsbn(),
                copy2);

        // -------------------------------
        // Register Members
        // -------------------------------

        Member student =
        new Member(
                "M001",
                "Rahul",
                MemberType.STUDENT);

Member regular =
        new Member(
                "M002",
                "Amit",
                MemberType.REGULAR);

Member premium =
        new Member(
                "M003",
                "Vikram",
                MemberType.PREMIUM);

        library.registerMember(student);
        library.registerMember(regular);
        library.registerMember(premium);

        // -------------------------------
        // Borrow
        // -------------------------------

        Loan loan1 =
                library.borrowBook(
                        "M001",
                        "9780132350884");

        System.out.println(
                "Loan created: " +
                        loan1);

        Loan loan2 =
                library.borrowBook(
                        "M002",
                        "9780132350884");

        System.out.println(
                "Loan created: " +
                        loan2);

        // -------------------------------
        // Third member
        // -------------------------------

        Member member3 =
                new Member(
                        "M003",
                        "Vikram",
                        MemberType.PREMIUM);

        library.registerMember(member3);

        try {

            library.borrowBook(
                    "M003",
                    "9780132350884");

        } catch (Exception e) {

            System.out.println(
                    e.getMessage());
        }

        // -------------------------------
        // Return
        // -------------------------------

        double fine =
                library.returnBook(
                        "LOAN-1");

        System.out.println(
                "Fine = ₹" + fine);
    }
}