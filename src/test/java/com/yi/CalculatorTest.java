
package com.yi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class CalculatorTest {

    private final Calculator c = new Calculator();

    private static final double EPSILON = 1e-9;

    // ===== 1. Basic arithmetic =====

    @Test
    void addWorks() {
        assertThat(c.eval("2", "+", "2"))
                .isCloseTo(4.0, within(EPSILON));
    }

    @Test
    void subtractWorks() {
        assertThat(c.eval("8", "-", "5"))
                .isCloseTo(3.0, within(EPSILON));
    }

    @Test
    void multiplyWorks() {
        assertThat(c.eval("2", "*", "3"))
                .isCloseTo(6.0, within(EPSILON));
    }

    @Test
    void divideWorks() {
        assertThat(c.eval("10", "/", "5"))
                .isCloseTo(2.0, within(EPSILON));
    }

    // ===== 2. Exception handling =====

    @Test
    void blankInputThrows() {
        assertThatThrownBy(() -> c.eval("", "+", "2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input is blank");
    }

    @Test
    void blankRightInputThrows() {
        assertThatThrownBy(() -> c.eval("2", "+", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input is blank");
    }

    @Test
    void divideByZeroThrows() {
        assertThatThrownBy(() -> c.eval("10", "/", "0"))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("division by zero");
    }

    @Test
    void invalidNumberThrows() {
        assertThatThrownBy(() -> c.eval("abc", "+", "2"))
                .isInstanceOf(NumberFormatException.class);
    }


    @Test
    void unsupportedOperatorThrows() {
        assertThatThrownBy(() -> c.eval("2", "?", "3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("?");
    }


    // ===== 3. Additional edge cases =====

    @Test
    void negativeZeroDivisorThrows() {
        assertThatThrownBy(() -> c.eval("10", "/", "-0"))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("division by zero");
    }

    @Test
    void whitespaceInputThrows() {
        assertThatThrownBy(() -> c.eval("   ", "+", "2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input is blank");
    }

    @Test
    void nullInputThrows() {
        assertThatThrownBy(() -> c.eval(null, "+", "2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input is blank");
    }

    @Test
    void invalidRightNumberThrows() {
        assertThatThrownBy(() -> c.eval("2", "+", "abc"))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    void negativeDivisionWorks() {
        assertThat(c.eval("-9", "/", "3"))
                .isCloseTo(-3.0, within(EPSILON));
    }

    @Test
    void floatingPointPrecisionWorks() {
        assertThat(c.eval("0.1", "+", "0.2"))
                .isCloseTo(0.3, within(EPSILON));
    }

    // ===== 4. Parameterized testing =====

    @ParameterizedTest(
            name = "[{index}] {0} {1} {2} = {3}"
    )
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

        assertThat(c.eval(a, operator, b))
                .as("Calculation: %s %s %s", a, operator, b)
                .isCloseTo(expected, within(EPSILON));
    }
}
