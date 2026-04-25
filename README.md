# Task Queue Service

Сервис очереди задач на обработку файлов. Принимает задачи, ставит их в очередь и асинхронно обрабатывает в фоне.

## Стек

- Kotlin + Spring Boot 4.x
- PostgreSQL + Flyway
- Spring Data JPA
- Kotlin Coroutines
- OpenAPI / Swagger
- Docker + Docker Compose


Воркеры запускаются при старте приложения и непрерывно разбирают очередь. Количество одновременно работающих воркеров задаётся в конфиге YAML. Задачи с более высоким приоритетом обрабатываются первыми.

## Статусы задачи
PENDING → IN_PROGRESS → DONE
→ FAILED → PENDING (retry)
PENDING → CANCELLED

## Swagger UI

После запуска документация доступна по адресу:
http://localhost:8080/v1/api/swagger-ui.html

## Запуск

### Требования

- Docker + Docker Compose
- Java 24 (для локальной разработки без Docker)

### Через Docker Compose

```bash
docker-compose up --build
```

Приложение будет доступно на `http://localhost:8080`

### Локально

1. Запустить PostgreSQL
```bash
docker-compose up postgres
```

2. Запустить приложение:
```bash
./gradlew bootRun
```
