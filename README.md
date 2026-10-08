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
| JUnit Test Executions | 23 |
| Passed | 23 |
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

## Bonus: AssertJ Testing

This project has been enhanced with AssertJ to provide fluent and expressive assertions while retaining JUnit 5 as the testing framework.

### Test Enhancements

- Migrated result assertions to AssertJ
- Added exception message validation
- Tested division by negative zero
- Tested null and whitespace inputs
- Tested invalid numeric operands
- Added floating-point precision checks
- Retained 8 parameterized test cases

### Updated Results

| Metric | Result |
|---|---|
| Test Executions | 23 |
| Passed | 23 |
| Failed | 0 |
| Errors | 0 |
| Calculator Line Coverage | 100% |
| Calculator Branch Coverage | 100% |

### Testing Frameworks

- JUnit 5
- AssertJ 3.27.7
- JaCoCo 0.8.15