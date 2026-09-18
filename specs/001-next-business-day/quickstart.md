# Quickstart: Validate the Next Business Day Feature

## Prerequisites

- Java 17 installed
- Repository dependencies resolved via Gradle wrapper
- Spring Boot application can start locally

## Run the service

```bash
./gradlew bootRun
```

## Validate happy-path scenarios

```bash
curl "http://localhost:8080/api/business-days/next?date=2026-09-07"
```

Expected outcome: a response containing `2026-09-08`.

```bash
curl "http://localhost:8080/api/business-days/next?date=2026-09-11"
```

Expected outcome: a response containing `2026-09-14`.

```bash
curl "http://localhost:8080/api/business-days/next?date=2026-05-22"
```

Expected outcome: a response containing `2026-05-26` when the following Monday is marked as a holiday.

The default configured calendar includes `2026-05-25` as a public holiday in
`src/main/java/com/example/demo/service/ConfiguredHolidayCalendar.java`.

## Validate invalid input scenarios

```bash
curl "http://localhost:8080/api/business-days/next?date=2026-02-30"
```

Expected outcome: HTTP 400 with a message describing the expected `yyyy-MM-dd` format.

## Validation checklist

- Weekend dates skip Saturday and Sunday correctly.
- Holiday-adjacent scenarios continue to the next valid working day.
- Invalid or empty dates fail fast with HTTP 400.
- Result payloads use ISO-formatted dates and are consistent for external consumers.
- The Gradle quality gate passes with `gradlew.bat test`.
