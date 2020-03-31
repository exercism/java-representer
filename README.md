# Exercism java-representer

Java representer takes a Java exercise solution and provides a normalized representation of it.
The representer implements the [representer interface](https://github.com/exercism/automated-analysis/blob/master/docs/representers/interface.md)


## Quickstart

### Docker

1. Create image

`sudo docker build --no-cache -t exercism/java-representer .`

2. Run the container

`sudo docker run  -v <EXERCISES_FOLDER>:/app/data exercism/java-representer <EXERCISE_SLUG> <EXERCISES_FOLDER>`
