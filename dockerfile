# образ Maven для сборки
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Копируем pom-файлы и зависимости для кэширования слоев Docker
COPY pom.xml .
COPY server/pom.xml ./server/
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY . .
RUN mvn clean package -pl server -am -DskipTests

# Финальный легкий образ с JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /app/server/target/*.SNAPSHOT.jar app.jar

# Точка входа для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080