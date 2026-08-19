package com.example.library;
import java.util.ArrayList;
import java.util.List;

public class Member {

    private final String memberId;
    private final String name;
    private final MemberType memberType;

    private final List<Loan> loans =
            new ArrayList<>();

    public Member(
            String memberId,
            String name,
            MemberType memberType) {

        this.memberId = memberId;
        this.name = name;
        this.memberType = memberType;
    }

    public int getMaximumBooks() {

        return switch (memberType) {

            case STUDENT -> 3;

            case REGULAR -> 5;

            case PREMIUM -> 10;
        };
    }

    public boolean canBorrow() {
        return getActiveLoanCount()
                < getMaximumBooks();
    }

    public int getActiveLoanCount() {

        return (int) loans.stream()
                .filter(loan -> !loan.isReturned())
                .count();
    }

    public int getRemainingBorrowingLimit() {

        return getMaximumBooks()
                - getActiveLoanCount();
    }

    public void addLoan(Loan loan) {

        if (!canBorrow()) {
            throw new IllegalStateException(
                    "Maximum borrowing limit reached");
        }

        loans.add(loan);
    }

    public String getMemberId() {
        return memberId;    
    }

    public String getName() {
        return name;   
    }
}
