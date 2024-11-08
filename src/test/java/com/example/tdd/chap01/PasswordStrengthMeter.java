package com.example.tdd.chap01;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {
        if(s == null || s.isEmpty()) return PasswordStrength.INVALID;
        boolean lengthEnough = s.length() >= 8;
        boolean containNumber = meetsContainingNumberCriteria(s);
        boolean containUpp = meetsContainingUppercaseCriteria(s);

        if(lengthEnough && !containNumber && !containUpp) return PasswordStrength.WEAK;

        if(!lengthEnough) return PasswordStrength.NORMAL;
        if(!containNumber) return PasswordStrength.NORMAL;
        if(!containUpp) return PasswordStrength.NORMAL;

        return PasswordStrength.STRONG;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        for(char ch: s.toCharArray()) {
            if(Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        for(char ch: s.toCharArray()) {
            if(ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }
}
