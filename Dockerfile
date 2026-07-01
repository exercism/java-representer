FROM gradle:9.5.0-jdk25@sha256:03305b464e024b29cfaad1c4a41fed61d06d15453176d2180f65bd4358b789a6 AS build

WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN gradle -i --stacktrace clean build

FROM eclipse-temurin:26.0.1_8-jdk@sha256:0e52e08108f27ab71328304e0c704ad53e2b6be812718ad38a2821c11ecb231d

ENV LOGGING_LEVEL=INFO

WORKDIR /opt/representer
COPY ./bin/run.sh bin/run.sh
COPY --from=build /app/build/libs/java-representer.jar .

ENTRYPOINT ["sh", "/opt/representer/bin/run.sh"]
