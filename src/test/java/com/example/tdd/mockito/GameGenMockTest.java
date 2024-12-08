package com.example.tdd.mockito;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class GameGenMockTest {

    @Test
    void mockTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(GameLevel.EASY)).willReturn("123");

        String num = genMock.generate(GameLevel.EASY);
        assertEquals("123", num);
    }

    @Test
    void mockThrowTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> genMock.generate(null));
    }

    @Test
    void voidMockMethodWillThrowTest() {
        List<String> mockList = mock(List.class);
        willThrow(UnsupportedOperationException.class)
                .given(mockList)
                .clear();

        assertThrows(
                UnsupportedOperationException.class,() -> mockList.clear());
    }

    @Test
    void anyMatchTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(any())).willReturn("456");

        String num = genMock.generate(GameLevel.EASY);
        assertEquals("456", num);

        String num2 = genMock.generate(GameLevel.NORMAL);
        assertEquals("456", num2);
    }

    @Test
    void mixAnyAndEq() {
        List<String> mockList = mock(List.class);
        given(mockList.set(anyInt(), eq("123"))).willReturn("456");
        String old = mockList.set(5, "123");

        assertEquals("456", old);
    }


}
