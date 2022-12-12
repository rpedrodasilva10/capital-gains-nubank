FROM maven:3.5-jdk-11-slim as build
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
ADD pom.xml $APP_HOME
RUN mvn verify --fail-never
ADD . $APP_HOME
RUN mvn package

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/capital-gains-jar-with-dependencies.jar /app.jar
ENTRYPOINT java -jar /app.jar