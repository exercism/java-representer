FROM gradle:8.7-jdk21 AS build

WORKDIR /app
COPY . /app
RUN gradle -i --stacktrace clean build

FROM eclipse-temurin:25

ENV LOGGING_LEVEL=INFO

WORKDIR /opt/representer
COPY ./bin/run.sh bin/run.sh
COPY --from=build /app/build/libs/java-representer.jar .

ENTRYPOINT ["sh", "/opt/representer/bin/run.sh"]
