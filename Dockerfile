# Estágio de Construção
FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /app

COPY . .

# Utilize um profile de construção (opcional, se aplicável)
# RUN mvn clean install -P build

# Estágio de Execução
FROM openjdk:11-jdk-slim

WORKDIR /app

COPY --from=build /app/target/java_1st_api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]