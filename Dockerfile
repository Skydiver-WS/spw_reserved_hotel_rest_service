FROM maven:3.9-amazoncorretto-23 as builder
LABEL authors="Aleksandr"

WORKDIR /app

# Копируем исходный код ДО сборки
COPY pom.xml .
COPY src ./src

# Собираем приложение с Maven профилями
RUN mvn clean package -Pvitrual-thread,prod -DskipTests

# Финальный образ
FROM amazoncorretto:23
LABEL authors="Aleksandr"

WORKDIR /app

# Копируем собранный JAR из стадии builder
COPY --from=builder /app/target/hotel_rest_service-*.jar app.jar

EXPOSE 8443
ENV SPRING_PROFILES_ACTIVE=vitrual-thread,prod
ENTRYPOINT ["java", "-jar", "app.jar"]