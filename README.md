# Java Representer

This repository contains the source code for the [Representer][representer-docs] used by the Java track on Exercism.
It takes a Java exercise solution submitted by a student and transforms it into a normalized representation.

## Contributing

If you want to contribute to the Java Representer, please refer to the [Contributing Guide][contributing-guide].

## Usage

### Running directly

Start by building the JAR using Gradle:

```sh
./gradlew build
```

Then, run the Java Representer using `build/libs/java-representer.jar`.
For example, to create a representation for a solution to the `leap` exercise, run:

```sh
java -jar build/libs/java-representer.jar leap /path/to/leap /path/to/output/folder
```

The Representer output can be found in the following files inside the `/path/to/output/folder` directory:

- `representation.txt` - Contains the normalized representation of the Java code inside the solution.
- `mapping.json` - Contains a mapping between the generated placeholders used in the representation and the original identifiers.
- `representation.json` - Contains metadata.

### Running with Docker

To run the Java Representer using Docker, first build and tag the Docker image:

```sh
docker build -t exercism/java-representer .
```

Then, run the image and mount the directory of the solution to represent.
For example, to create a representation for a solution to the `leap` exercise, run:

```sh
docker run -v /path/to/leap:/input -v /path/to/output/folder:/output exercism/java-representer leap /input /output
```

The Representer output can be found in the following files inside the `/path/to/output/folder` directory:

- `representation.txt` - Contains the normalized representation of the Java code inside the solution.
- `mapping.json` - Contains a mapping between the generated placeholders used in the representation and the original identifiers.
- `representation.json` - Contains metadata.

## Tests

### Unit tests

To run the unit tests:

```sh
./gradlew test
```

### Smoke tests

To run the smoke tests using Docker:

```sh
bin/run-tests-in-docker.sh
```

[contributing-guide]: https://github.com/exercism/java-representer/blob/main/CONTRIBUTING.md
[representer-docs]: https://exercism.org/docs/building/tooling/representers
