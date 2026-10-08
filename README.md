# Lab 4 — Test Your Calculator

## Project Overview

This project extends the Java calculator developed in Lab 3 with automated unit testing using JUnit 5 and code coverage analysis using JaCoCo.

## Features

- Addition, subtraction, multiplication and division
- Division-by-zero exception handling
- Invalid input and unsupported operator handling
- Parameterized tests with 8 test cases

## Testing Results

| Metric | Result |
|---|---|
| JUnit Test Executions | 17 |
| Passed | 17 |
| Failed | 0 |
| Calculator Line Coverage | 100% |
| Calculator Branch Coverage | 100% |
| Calculator Instruction Coverage | 100% |

## Bug Found and Fixed

The original calculator returned Infinity for floating-point division by zero. An explicit zero-divisor check was added to throw an ArithmeticException. All tests passed after the fix.

## Technologies

- Java 17 (compiler target)
- Maven
- JUnit 5
- JaCoCo 0.8.15

## Test Reports

The generated JaCoCo HTML report is available at `docs/jacoco/index.html`.

Screenshots documenting the test failure, bug fix and code coverage results are located in `docs/screenshots`.

## Running the Tests

Use Maven to run the unit tests. For coverage generation in the original Windows development environment, the JaCoCo data file was explicitly redirected to an ASCII-only temporary path due to a suspected path compatibility issue.
