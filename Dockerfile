FROM gradle:8.4-jdk17 AS build

WORKDIR /app
COPY . /app

RUN gradle -i clean build
RUN gradle -i shadowJar \
    && cp build/libs/java-representer.jar .

FROM eclipse-temurin:17
WORKDIR /opt/representer
COPY ./bin/run.sh bin/run.sh
COPY --from=build /app/build/libs/java-representer.jar .
ENTRYPOINT ["sh", "/opt/representer/bin/run.sh"]
