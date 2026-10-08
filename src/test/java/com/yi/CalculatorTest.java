package com.yi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator c = new Calculator();

    // 1. Addition
    @Test
    void addWorks() {
        assertEquals(4.0, c.eval("2", "+", "2"));
    }

    // 2. Division
    @Test
    void divideWorks() {
        assertEquals(2.0, c.eval("10", "/", "5"));
    }

    // 3. Blank input
    @Test
    void blankInputThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> c.eval("", "+", "2")
        );
    }

    // 4. Multiplication
    @Test
    void multiplyWorks() {
        assertEquals(6.0, c.eval("2", "*", "3"));
    }

    // 5. Subtraction
    @Test
    void subtractWorks() {
        assertEquals(3.0, c.eval("8", "-", "5"));
    }

    // 6. Division by zero
    @Test
    void divideByZeroThrows() {
        assertThrows(
                ArithmeticException.class,
                () -> c.eval("10", "/", "0")
        );
    }

    // 7. Blank right operand
    @Test
    void blankRightInputThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> c.eval("2", "+", "")
        );
    }

    // 8. Invalid number
    @Test
    void invalidNumberThrows() {
        assertThrows(
                NumberFormatException.class,
                () -> c.eval("abc", "+", "2")
        );
    }

    // 9. Unsupported operator
    @Test
    void unsupportedOperatorThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> c.eval("2", "?", "3")
        );
    }

    // 10. Parameterized test: 8 cases
    @ParameterizedTest
    @CsvSource({
            "2, +, 3, 5",
            "7, -, 2, 5",
            "4, *, 3, 12",
            "10, /, 2, 5",
            "-4, +, 1, -3",
            "1.5, *, 2, 3",
            "0, -, 8, -8",
            "9, /, 3, 3"
    })
    void calculatorParameterizedTest(
            String a,
            String operator,
            String b,
            double expected) {

        double actual = c.eval(a, operator, b);

        assertEquals(expected, actual, 1e-9);
    }
}
