FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV MYSQL_URL=jdbc:mysql://localhost:3306/muebleria_db
ENV MYSQL_USER=root
ENV MYSQL_PASS=root

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]