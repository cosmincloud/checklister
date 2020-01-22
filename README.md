# checklister

A backend service for managing checklists.

## Subprojects

The Checklister application is split up into several subprojects,
contained in the `subprojects` directory.

## Running

```
./gradlew composeUp
```

Checklister will be available on [localhost:8180](http://localhost:8180).

## Integration tests

Running the integration tests via the Gradle task:

```
./gradlew :integrationTest:test
```


## Publishing

```
./gradlew clean build :web:bootJar
docker build -t blacktower:5000/cosapps/checklister:<version> .
docker push blacktower:5000/cosapps/checklister:<version>
```

## Generating the Client Library

```
./gradlew :client:openApiGenerate
cd subprojects/client/build/openapi
cp ../../build.gradle.custom build.gradle
chmod a+x gradlew
./gradlew
