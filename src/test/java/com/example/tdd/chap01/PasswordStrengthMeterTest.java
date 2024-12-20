package com.example.tdd.chap01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }

    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    @Test
    void meetsOtherCriteria_expect_for_length_then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_expect_for_number_then_normal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    void emptyString_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void meetOtherCriteria_expect_for_Uppercase_Then_Normal() {
        assertStrength("ab12#$df", PasswordStrength.NORMAL);
    }

    @Test
    void meetOnlyLengthCriteria_Then_Weak() {
        assertStrength("abdefefe", PasswordStrength.WEAK);
    }

    @Test
    void meetOnlyNumberCriteria_Then_Weak() {assertStrength("12345", PasswordStrength.WEAK);}

    @Test
    void meetOnlyUpperCase_then_weak() {assertStrength("ABCDE", PasswordStrength.WEAK);}

    @Test
    void meetNoCriteria_Then_Weak() {assertStrength("abc", PasswordStrength.WEAK);}
}
