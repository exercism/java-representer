FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY . /app 
RUN mvn package

FROM openjdk:11-jdk-slim
WORKDIR /opt/representer
COPY ./bin/generate.sh bin/generate.sh
COPY --from=build /app/target/java-representer.jar .
ENTRYPOINT ["sh", "/opt/representer/bin/generate.sh"]
