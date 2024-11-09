package com.example.tdd.chap02;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonth = payData.getPayAmount() >= 100_000 ? (payData.getPayAmount() / 10_000) + 2 : payData.getPayAmount() / 10_000;

        if(payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonth);
        } else {
            return payData.getBillingDate().plusMonths(addedMonth);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonth) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonth);
        if(!isSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)) {

            final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();

            if(dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else {
            return candidateExp;
        }
    }

    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2) {
        return date1.getDayOfMonth() == date2.getDayOfMonth();
    }

    private int lastDayOfMonth(LocalDate date) {
        return YearMonth.from(date).lengthOfMonth();
    }
}
