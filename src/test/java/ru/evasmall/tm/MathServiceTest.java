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
        assertEquals(1, mathService.factorialLong("0"));
        assertEquals(24, mathService.factorialLong("4"));
        assertEquals("1", mathService.factorialBigInteger("0", 2).toString());
        assertEquals("24", mathService.factorialBigInteger("4", 2).toString());
        assertEquals("1", mathService.factorialNoThread("0", 2).toString());
        assertEquals("24", mathService.factorialNoThread("4", 2).toString());
    }

    @Test
    void factorialException() {
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialLong("-3"));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialLong("строка"));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialLong(""));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialLong("99"));

        assertThrows(IllegalArgumentException.class, () -> mathService.factorialBigInteger("-3",5));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialBigInteger("строка", 5));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialBigInteger("", 5));

        assertThrows(IllegalArgumentException.class, () -> mathService.factorialNoThread("-3", 3));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialNoThread("строка", 3));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialNoThread("", 2));
        assertThrows(IllegalArgumentException.class, () -> mathService.factorialNoThread("5", -5));
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