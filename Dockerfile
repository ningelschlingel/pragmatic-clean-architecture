FROM gradle:9.2-jdk25 AS build  
WORKDIR /app
COPY . .
# --no-daemon is recommended for CI/Docker
RUN ./gradlew bootJar --no-daemon 

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar
# Use this if failing: COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]