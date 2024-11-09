package com.example.tdd.chap02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    private void assertExpiryDate(PayData payData, LocalDate expectExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectExpiryDate, realExpiryDate);
    }

    @Test
    @DisplayName("만원 납부 하면 한달 뒤 만료일 됨")
    void oneMonthTest() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,4,1));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,5,5))
                        .payAmount(10_000).build(),
                LocalDate.of(2019,6,5));
    }

    @Test
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음")
    // 1.31 -> 2.28, 5.31 -> 6.30
    void notSameDate() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,31))
                        .payAmount(10_000).build(),
                LocalDate.of(2019,2,28));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,5,31))
                        .payAmount(10_000).build(),
                LocalDate.of(2019,6,30));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020,1,31))
                        .payAmount(10_000).build(),
                LocalDate.of(2020,2,29));
    }

    @Test
    @DisplayName("첫 납부일과 만료일 일자가 다를때 만원 납부")
    void firstExpiryDate() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData, LocalDate.of(2019,3,31));

        // 첫 납부 일이 2019-01-30, 만료 되는 2019-02-28에 1만원을 납부 하면 다음 만료 일은 2019-03-30
        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,30))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData2, LocalDate.of(2019,3,30));

        // 첫 납부 일이 2019-05-31이고 만료 일인 2019-06-30에 1만원을 납부 하면 다음 만료 일은 2019-07-31
        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,5,31))
                .billingDate(LocalDate.of(2019,6,30))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData3, LocalDate.of(2019,7,31));

    }

    @Test
    @DisplayName("이만원 이상 납부하면 비례해서 만료일 계산")
    void calculateExpiryTest() {
        // 2만원을 지불하면 만료일이 두 달 뒤가 된다.
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019,5,1)
        );

        // 3만원을 지불하면 만료일이 석 달 뒤가 된다.
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019,6,1)
        );
    }

    @Test
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 이만원 이상 납부")
    void differentDays() {
        // 첫 납부 일이 2019-01-31이며 만료 되는 2019-02-28에 2만원을 납부 하면 다음 만료 일은 2019-04-30이다.
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,1,31))
                        .billingDate(LocalDate.of(2019,2,28))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019,4,30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,1,31))
                        .billingDate(LocalDate.of(2019,2,28))
                        .payAmount(40_000)
                        .build(),
                LocalDate.of(2019,6,30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,3,31))
                        .billingDate(LocalDate.of(2019,4,30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019,7,31)
        );
    }

    @Test
    @DisplayName("10만원을 납부하면 1년 제공")
    void paid10000wonTest() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020,1,28)
        );
    }

    @Test
    @DisplayName("윤달에 10만원을 납부하면 1년 제공")
    void moonMonth100000wonTEst() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020,2,29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021,2,28)
        );
    }

    @Test
    @DisplayName("13만원을 납부하면 1년 3개월 제공")
    void Month13PaidTest() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,2))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2020,4,2)
        );
    }

}
