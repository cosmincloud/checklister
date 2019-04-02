# checklister

A backend service for managing checklists.

## Subprojects

The Checklister application is split up into several subprojects,
contained in the `subprojects` directory.

## Usage

Running:

```
docker-compose down                && \
docker-compose up -d db            && \
sleep 10                           && \
./gradlew clean build              && \
docker-compose down                && \
docker-compose up -d --build       && \
docker-compose logs -f checklister
```

Publishing:
```
./gradlew clean build :checklister-web:bootJar
docker build -t blacktower:5000/cosapps/checklister:<version> .
docker push docker build -t blacktower:5000/cosapps/checklister:<version>
```
