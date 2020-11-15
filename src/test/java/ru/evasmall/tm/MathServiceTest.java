package ru.evasmall.tm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathServiceTest {

    private MathService mathService;

    @BeforeEach
    void setUp() {
        mathService = new MathService();
    }

    @Test
    void sumCorrect() {
        assertEquals(6, mathService.sum("3","3"));
        assertEquals(-4, mathService.sum("-2","-2"));
    }

    @Test
    void sumException() {
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3.5", "3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3", "3.5"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3,5", "3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3", "3,5"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("строка", "3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3", "строка"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("999999999999999999999999999999999999", "1"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("", "3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.sum("3", ""));
    }


    @Test
    void factorialCorrect() {
        assertEquals(1, mathService.factorial("0"));
        assertEquals(24, mathService.factorial("4"));
    }

    @Test
    void factorialException() {
        assertThrows(IllegalArgumentException.class, () -> mathService.factorial("-3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorial("строка"));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorial(""));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorial("99"));
    }

    @Test
    void fibonacciCorrect() {
        long[] sequence = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        assertArrayEquals(sequence, mathService.fibonacci("55"));
    }

    @Test
    void fibonacciException() {
        assertThrows(IllegalArgumentException.class, () -> mathService.fibonacci("54"));
        assertThrows(IllegalArgumentException.class, () -> mathService.fibonacci("-55"));
        assertThrows(IllegalArgumentException.class, () -> mathService.fibonacci("строка"));
        assertThrows(IllegalArgumentException.class, () -> mathService.fibonacci("99999999999999999999999999"));
    }

}