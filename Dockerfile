
FROM maven AS build
COPY src /home/application/src
COPY pom.xml /home/application
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/application/pom.xml clean package

FROM openjdk:19-alpine
COPY --from=build /home/application/target/east-trade.jar /usr/local/lib/east-trade.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/east-trade.jar"]
