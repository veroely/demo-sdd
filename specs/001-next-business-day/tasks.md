---
description: "Task list for Next Business Day"
---

# Tasks: Next Business Day

**Input**: Design documents from `/specs/001-next-business-day/`
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/`, and `quickstart.md`

## Phase 1: Setup

**Purpose**: Establish the Spring Boot package structure and test locations required by the implementation plan.

- [ ] T001 Create the feature package structure under `src/main/java/com/example/demo/controller`, `src/main/java/com/example/demo/service`, `src/main/java/com/example/demo/model`, and `src/main/java/com/example/demo/validation`.
- [ ] T002 [P] Create matching test package structure under `src/test/java/com/example/demo/controller`, `src/test/java/com/example/demo/service`, and `src/test/java/com/example/demo/validation`.
- [ ] T003 [P] Confirm Java 17, Spring Web, and JUnit 5 configuration in `build.gradle` without adding dependencies unrelated to the feature.

---

## Phase 2: Foundational

**Purpose**: Implement shared contracts and error handling that block both user stories.

- [ ] T004 Define the holiday-calendar abstraction and configured holiday source in `src/main/java/com/example/demo/service/HolidayCalendar.java` and `src/main/java/com/example/demo/service/ConfiguredHolidayCalendar.java`.
- [ ] T005 [P] Define the response DTO with ISO date fields in `src/main/java/com/example/demo/model/NextBusinessDayResponse.java`.
- [ ] T006 [P] Define the validation error DTO in `src/main/java/com/example/demo/model/ValidationErrorResponse.java`.
- [ ] T007 Implement shared invalid-date exception handling for HTTP 400 responses in `src/main/java/com/example/demo/validation/InvalidDateException.java` and `src/main/java/com/example/demo/validation/ApiExceptionHandler.java`.
- [ ] T008 [P] Add focused tests for error payload shape and HTTP 400 mapping in `src/test/java/com/example/demo/validation/ApiExceptionHandlerTest.java`.

**Checkpoint**: Shared models, holiday access, and validation error handling are available before story implementation begins.

---

## Phase 3: User Story 1 - Calculate the next valid business day (Priority: P1)

**Goal**: Return the first valid business date strictly after the supplied date, excluding weekends and configured public holidays.

**Independent Test**: Service and HTTP tests cover a regular business day, a Friday, a weekend date, and a Friday followed by a configured Monday holiday.

### Tests for User Story 1

- [ ] T009 [P] [US1] Add service tests for regular business-day, Friday, weekend, and holiday-adjacent calculations in `src/test/java/com/example/demo/service/NextBusinessDayServiceTest.java`.
- [ ] T010 [P] [US1] Add controller integration tests for `GET /api/business-days/next` success responses and ISO payload fields in `src/test/java/com/example/demo/controller/NextBusinessDayControllerTest.java`.

### Implementation for User Story 1

- [ ] T011 [P] [US1] Implement the `NextBusinessDayService` using `LocalDate`, advancing one day at a time until the date is neither Saturday/Sunday nor a configured holiday in `src/main/java/com/example/demo/service/NextBusinessDayService.java`.
- [ ] T012 [US1] Configure the holiday calendar with the acceptance-test holiday `2026-05-25` and a maintainable source boundary in `src/main/java/com/example/demo/service/ConfiguredHolidayCalendar.java`.
- [ ] T013 [US1] Implement the `GET /api/business-days/next` endpoint with required `date` query parameter in `src/main/java/com/example/demo/controller/NextBusinessDayController.java`.
- [ ] T014 [US1] Return `inputDate` and `nextBusinessDay` as `yyyy-MM-dd` values matching `specs/001-next-business-day/contracts/next-business-day-api.md` from `src/main/java/com/example/demo/model/NextBusinessDayResponse.java`.
- [ ] T015 [US1] Wire the controller to the service and verify that the result is strictly after the input date in `src/main/java/com/example/demo/controller/NextBusinessDayController.java` and `src/main/java/com/example/demo/service/NextBusinessDayService.java`.

**Checkpoint**: User Story 1 is independently usable through the documented API and passes all four valid-date acceptance scenarios.

---

## Phase 4: User Story 2 - Reject invalid or missing date input (Priority: P1)

**Goal**: Reject null, empty, malformed, and impossible calendar dates with HTTP 400 and a clear ISO-format message.

**Independent Test**: Requests with `2026-02-30`, an empty value, and a missing parameter each produce HTTP 400 with the documented error contract.

### Tests for User Story 2

- [ ] T016 [P] [US2] Add controller tests for malformed, impossible, empty, and missing `date` inputs returning HTTP 400 in `src/test/java/com/example/demo/controller/NextBusinessDayValidationTest.java`.
- [ ] T017 [P] [US2] Add parsing tests proving strict `yyyy-MM-dd` validation rejects short-date, time-zone, and locale-formatted values in `src/test/java/com/example/demo/validation/DateInputParserTest.java`.

### Implementation for User Story 2

- [ ] T018 [US2] Implement strict `yyyy-MM-dd` parsing and reject missing, empty, malformed, or impossible dates before business-day calculation in `src/main/java/com/example/demo/validation/DateInputParser.java`.
- [ ] T019 [US2] Connect parser failures and missing query parameters to `ValidationErrorResponse` with error code `INVALID_DATE` and the expected-format message in `src/main/java/com/example/demo/controller/NextBusinessDayController.java` and `src/main/java/com/example/demo/validation/ApiExceptionHandler.java`.
- [ ] T020 [US2] Ensure invalid input never invokes holiday lookup or date-advance logic in `src/main/java/com/example/demo/controller/NextBusinessDayController.java` and `src/main/java/com/example/demo/service/NextBusinessDayService.java`.

**Checkpoint**: User Stories 1 and 2 both work independently, with valid requests calculated and invalid requests rejected consistently.

---

## Phase 5: Polish & Cross-Cutting Concerns

**Purpose**: Complete operational evidence, documentation, and quality gates for the feature.

- [ ] T021 [P] Add structured logging for successful calculations, validation failures, and holiday-calendar failures in `src/main/java/com/example/demo/controller/NextBusinessDayController.java` and `src/main/java/com/example/demo/service/NextBusinessDayService.java`.
- [ ] T022 [P] Update `specs/001-next-business-day/quickstart.md` with the implemented holiday-calendar configuration and executable validation commands.
- [ ] T023 Run the full Gradle test suite and verify the documented build/test quality gate with `gradlew.bat test`.

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies; establishes package and build structure.
- **Foundational (Phase 2)**: Depends on Setup and blocks both user stories.
- **User Story 1 (Phase 3)**: Depends on Foundational; delivers the MVP calculation API.
- **User Story 2 (Phase 4)**: Depends on Foundational and integrates with the endpoint from User Story 1; its parser tests can begin after shared validation types exist.
- **Polish (Phase 5)**: Depends on both user stories being implemented.

### User Story Dependencies

- **User Story 1 (P1)**: Starts after Phase 2 and has no dependency on another story.
- **User Story 2 (P1)**: Starts after Phase 2; shares the endpoint and error boundary with User Story 1, so integration tasks follow the initial endpoint implementation.

### Parallel Opportunities

- T002 and T003 can run in parallel after setup begins.
- T005, T006, and T008 can run in parallel after the shared package structure exists.
- T009 and T010 can be written in parallel before the User Story 1 implementation tasks.
- T011 and T012 can be implemented in parallel because they are separate service files, then T013-T015 integrate them.
- T016 and T017 can be written in parallel before User Story 2 implementation.
- T021 and T022 can be completed in parallel after both stories are implemented.

## Parallel Example: User Story 1

```text
Task: "[US1] Add service tests in src/test/java/com/example/demo/service/NextBusinessDayServiceTest.java"
Task: "[US1] Add controller integration tests in src/test/java/com/example/demo/controller/NextBusinessDayControllerTest.java"
Task: "[US1] Implement NextBusinessDayService in src/main/java/com/example/demo/service/NextBusinessDayService.java"
Task: "[US1] Configure holiday calendar in src/main/java/com/example/demo/service/ConfiguredHolidayCalendar.java"
```

## Parallel Example: User Story 2

```text
Task: "[US2] Add controller validation tests in src/test/java/com/example/demo/controller/NextBusinessDayValidationTest.java"
Task: "[US2] Add strict parser tests in src/test/java/com/example/demo/validation/DateInputParserTest.java"
```

## Implementation Strategy

### MVP First

1. Complete Phase 1 and Phase 2.
2. Implement User Story 1 with its service and controller tests.
3. Run the User Story 1 tests and demonstrate the four valid-date scenarios.
4. Add User Story 2 validation and error handling.
5. Run the complete Gradle test suite and validate the quickstart commands.

### Incremental Delivery

1. Foundation ready: shared DTOs, holiday abstraction, and error boundary.
2. Add User Story 1: valid next-business-day API.
3. Add User Story 2: strict invalid-input handling.
4. Complete observability, documentation, and quality validation.

## Notes

- Every task uses the required checkbox, sequential ID, applicable parallel marker, story label, and exact file path.
- The implementation must preserve Java 17, Spring Boot, `LocalDate`, and the ISO `yyyy-MM-dd` contract.
- The constitution requires test-first evidence, quality-gate execution, and actionable runtime error handling.
