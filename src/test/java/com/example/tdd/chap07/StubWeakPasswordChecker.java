package com.example.tdd.chap07;

public class StubWeakPasswordChecker implements WeakPasswordChecker {

    public boolean weak;

    public void setWeak(boolean weak) {
        this.weak = weak;
    }

    @Override
    public boolean checkPasswordWeak(String password) {
        return weak;
    }
}
