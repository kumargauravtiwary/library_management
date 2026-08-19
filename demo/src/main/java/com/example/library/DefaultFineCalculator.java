package com.example.library;
public class DefaultFineCalculator implements FineCalculator {

    private static final double DAILY_FINE = 5.0;

    @Override
    public double calculate(long overdueDays) {
        return overdueDays * DAILY_FINE;
    }
}
