# Calculator | Lab 4

[![Java Calculator CI](https://github.com/yinghuayaya/calculator-lab4/actions/workflows/ci-calculator.yml/badge.svg?branch=main)](https://github.com/yinghuayaya/calculator-lab4/actions/workflows/ci-calculator.yml)

基于 Lab 3 的 Java CLI Calculator，使用 JUnit 5、AssertJ 进行单元测试，并通过 JaCoCo 分析代码覆盖率。

本次实验重点包括测试用例设计、异常处理、Bug 修复及自动化构建。

## 1. Project Info / 项目信息

**Development Environment**
- Java 17 (Compiler Target)
- Maven
- Apache Commons Lang 3.14.0
- JUnit 5.10.0
- AssertJ 3.27.7
- JaCoCo 0.8.15

**Calculator Features**
- 支持 `+`、`-`、`*`、`/` 四则运算
- 支持负数及浮点数输入
- 对空输入、非法数字和未知运算符进行异常处理
- 除数为零时抛出 `ArithmeticException`

## 2. Test Design / 测试设计

测试代码位于 `src/test/java/com/yi/CalculatorTest.java`。

| Test Type | 测试内容 |
|---|---|
| Basic Operations | 加减乘除、负数及小数 |
| Exception Handling | 除零、非法数字、未知运算符 |
| Boundary Cases | 空字符串、空格、null、负零 |
| Floating-point | 使用误差容限检查浮点运算结果 |
| Parameterized Tests | 8 组 CSV 测试数据 |

使用 JUnit 5 组织测试，AssertJ 的 `assertThat()`、`assertThatThrownBy()` 完成结果与异常断言。

最终包含 **15 个普通测试和 8 组参数化测试，共 23 次测试执行**。

## 3. Bug Fix / 缺陷修复

**Issue:** 原始计算器执行 `10 / 0` 时返回 `Infinity`，而不是预期的异常。

**Cause:** Java 的 `double` 浮点除零不会自动抛出 `ArithmeticException`。

**Fix:** 在除法分支增加零除数判断：

```java
if (y == 0.0) {
    throw new ArithmeticException("division by zero");
}
```

修复后重新运行全部测试，除零测试通过，原有功能未受到影响。

## 4. Test Results / 测试结果

| Metric | Result |
|---|---|
| Test Executions | 23 |
| Passed / Failed | 23 / 0 |
| Calculator Line Coverage | 100% (13/13) |
| Calculator Branch Coverage | 100% (11/11) |
| Calculator Instruction Coverage | 100% (59/59) |

Calculator 类达到 100% 行覆盖率和分支覆盖率。

项目整体行覆盖率为 **72.22%**，主要原因是 `App.main()` 未纳入当前单元测试范围。

## 5. Build & Run / 构建与运行

在项目根目录执行：

**Run Unit Tests**

```powershell
mvn clean test
```

**Generate Coverage Report**

```powershell
mvn clean verify
```

本地 Windows 环境中曾遇到 JaCoCo 执行数据文件未生成的问题。通过指定临时数据路径完成覆盖率采集：

```powershell
$coverageDir = Join-Path $env:TEMP "jacoco-lab4"
New-Item -ItemType Directory -Force -Path $coverageDir | Out-Null
$coverageFile = Join-Path $coverageDir "jacoco.exec"

mvn clean verify "-Djacoco.destFile=$coverageFile" "-Djacoco.dataFile=$coverageFile"
```

HTML 报告默认生成于 `target/site/jacoco/index.html`。

## 6. Project Structure / 文件结构

| File / Directory | Description |
|---|---|
| `src/main/java/com/yi/Calculator.java` | Calculator 核心运算逻辑 |
| `src/main/java/com/yi/App.java` | CLI 程序入口 |
| `src/test/java/com/yi/CalculatorTest.java` | JUnit 5 + AssertJ 测试 |
| `pom.xml` | Maven 依赖与插件配置 |
| `docs/jacoco/` | JaCoCo Coverage Report |
| `docs/screenshots/` | 测试过程及结果截图 |

## 7. Reports / 实验记录

- [JaCoCo Coverage Report](docs/jacoco/index.html)
- [Test Screenshots](docs/screenshots)

## 8. Lab 5: Continuous Integration

GitHub Actions runs **Java Calculator CI** on pushes to `main` and pull requests targeting `main`.
The workflow uses Ubuntu and Eclipse Temurin **JDK 17 and 21**, with Maven dependency caching.
Both matrix jobs resolve dependencies, run `mvn clean compile`, and run `mvn test`.
The project retains its Java 17 compiler target and the existing 23 JUnit 5 / AssertJ test executions.

Maven logs and JUnit XML reports are uploaded as separate artifacts for each JDK, including when tests fail.
See [the workflow](.github/workflows/ci-calculator.yml) and [actual CI runs](https://github.com/yinghuayaya/calculator-lab4/actions/workflows/ci-calculator.yml).

## 9. Lab 6: Containerization

Use JDK 17 and Maven to build a standalone executable JAR:

```sh
mvn clean test
mvn clean package
java -jar target/calculator-1.0-SNAPSHOT.jar
java -jar target/calculator-1.0-SNAPSHOT.jar 10 / 5
```

The default run evaluates `12 + 3`, `12 - 3`, `12 * 3`, and `12 / 3` through
the original calculator. The three-argument interface still prints one numeric
result. Maven Shade includes Apache Commons Lang and sets `Main-Class: com.yi.App`.

Build and run the Linux image with Docker Desktop or Docker Engine:

```sh
docker build -t calculator:v1 .
docker images
docker run --rm calculator:v1
docker run --rm calculator:v1 10 / 5
bash scripts/verify-container.sh
```

The multi-stage Dockerfile runs all 23 existing JUnit/AssertJ test executions
in a Maven/Temurin 17 build stage. Only the shaded JAR is copied into the
Temurin 17 JRE runtime image. The process runs as UID/GID 10001.

The existing GitHub Actions workflow retains the main-branch push/pull-request
triggers and JDK 17/21 tests. A dependent Docker job builds the image, verifies
all four operations and division-by-zero rejection, and preserves its logs.
There is no automatic registry publishing and no Docker Hub secret in CI.

For a manual release after Docker Hub browser authentication:

```sh
docker login
docker tag calculator:v1 yinghuayaya/calculator:lab6
docker push yinghuayaya/calculator:lab6
docker pull yinghuayaya/calculator:lab6
docker run --rm yinghuayaya/calculator:lab6
```

On Windows paths containing non-ASCII characters, if the JaCoCo report says
its data file is missing, map the existing project to an unused ASCII drive
letter for verification (PowerShell; do not replace an existing drive):

```powershell
$project = (Get-Location).Path
if (Test-Path L:\) { throw 'Choose an unused drive letter.' }
subst.exe L: $project
try {
    Set-Location L:\
    mvn clean verify
} finally {
    Set-Location $project
    subst.exe L: /D
}
```

Earlier sections describe Lab 4/5 evidence; new run records determine Lab 6 outcomes.
