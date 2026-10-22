# Build and test with Maven and a Java 17 JDK.
FROM maven:3.9-eclipse-temurin-17 AS build

# Keep build inputs in a dedicated directory.
WORKDIR /build

# Copy the Maven configuration and application/test sources.
COPY pom.xml .
COPY src ./src

# Run the unit tests and create the executable shaded JAR.
RUN mvn -B -ntp package

# Run with a Java 17 JRE; Maven and sources stay in the build stage.
FROM eclipse-temurin:17-jre-jammy

# Use a predictable runtime directory.
WORKDIR /app

# Copy only the executable JAR, including its runtime dependency.
COPY --from=build /build/target/calculator-1.0-SNAPSHOT.jar calculator.jar

# The calculator needs no privileged access.
USER 10001:10001

# Run the demo by default, or pass three calculator arguments.
ENTRYPOINT ["java", "-jar", "calculator.jar"]
