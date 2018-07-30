# checklister

A backend service for managing checklists.

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

