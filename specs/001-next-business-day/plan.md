# Implementation Plan: Next Business Day

**Branch**: `001-next-business-day` | **Date**: 2026-09-18 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from [spec.md](./spec.md)

## Summary

This feature adds a backend capability to calculate the next valid business day from a supplied date. The implementation excludes weekends and configured public holidays, then validates malformed or missing input before any calculation is attempted.

The solution fits the existing Spring Boot repository by keeping the logic in a small service/controller boundary with explicit validation and clear response contracts. It follows the project constitution by favoring a simple design, clear test coverage, and observable behavior for operational callers.

## Technical Context

**Language/Version**: Java 17 with the existing Spring Boot project structure

**Primary Dependencies**: Spring Boot, Spring Web, Java Time (`LocalDate`, `DayOfWeek`), JUnit 5

**Storage**: No persistent storage is required for v1; holiday data comes from the configured holiday calendar source or static business rules

**Testing**: JUnit 5, Spring Boot test slices, service-level and controller-level validation tests

**Target Platform**: Backend service/API integration layer

**Project Type**: web-service

**Performance Goals**: Single-date business-day lookup completes in under 100 ms in a local service environment

**Constraints**: Dates must be provided in ISO format; weekends and configured holidays are always non-working days; no timezone ambiguity in v1

**Scale/Scope**: Supports integrated consumer workflows and operational scheduling; not a full scheduler engine or multi-service platform

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Passes User Value & Clarity: the feature directly supports operational scheduling and avoids manual calendar checks.
- Passes Simplicity & Maintainability: a single service + controller boundary is sufficient for the requirement.
- Passes Test-First Delivery: the acceptance criteria can be converted into failing tests before implementation.
- Passes Quality Gates & Safe Change: the change is small, reviewable, and backed by validation evidence.
- Passes Observability & Operational Readiness: the API returns predictable success and validation-failure responses.

## Project Structure

### Documentation (this feature)

```text
specs/001-next-business-day/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/           # Phase 1 output
├── spec.md              # Feature specification
└── tasks.md             # Task list for implementation
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── model/
│   │       └── validation/
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/demo/
```

**Structure Decision**: A single Spring Boot backend service is sufficient for this feature and matches the repository structure. No separate module or multi-project layout is needed for v1.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

No constitution violations require special justification for this feature.
