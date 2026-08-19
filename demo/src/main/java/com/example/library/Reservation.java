package com.example.library;
import java.time.LocalDateTime;

public class Reservation {

    private final String reservationId;
    private final Book book;
    private final Member member;
    private final LocalDateTime reservationTime;

    public Reservation(
            String reservationId,
            Book book,
            Member member) {

        this.reservationId = reservationId;
        this.book = book;
        this.member = member;
        this.reservationTime = LocalDateTime.now();
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
}
