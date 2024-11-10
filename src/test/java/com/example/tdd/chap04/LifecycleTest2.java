package com.example.tdd.chap04;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LifecycleTest2 {

    LifecycleTest2() {
        System.out.println("lifecycle start...");
    }

    @BeforeAll
    static void before() {
        System.out.println("before all...");
    }

    @Test
    void a() {
        System.out.println("a");
    }

    @Test
    void b() {
        System.out.println("b");
    }

    @AfterAll
    static void after() {
        System.out.println("after...");
    }
}
