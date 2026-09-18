# Feature Specification: Next Business Day

**Feature Branch**: `001-next-business-day`

**Created**: 2026-09-18

**Status**: Draft

**Input**: User description: "Titulo: Consulta y cálculo del próximo día hábil considerando fines de semana y feriados\nDescripcion del PBI: Como servicio consumidor / sistema integrado\nQuiero consultar el próximo día hábil a partir de una fecha dada\nPara programar operaciones, transacciones y procesos operativos únicamente en fechas laborables válidas.\n\nCriterios de Aceptación:\nCaracterística: Obtener el próximo día hábil considerando calendario de feriados\n  Antecedentes:\n\n    Dado que el servicio de calendario y feriados se encuentra disponible y actualizado\n  CA1: Próximo día hábil en un día laboral regular (sin feriado adyacente)\n\n    Dado que se envía la fecha "2026-09-07" correspondiente a un lunes laboral\n\n    Cuando se solicita calcular el próximo día hábil\n\n    Entonces el sistema debe retornar la fecha "2026-09-08" correspondiente al martes\n  CA2: Próximo día hábil después de un viernes regular (fin de semana estándar)\n\n    Dado que se envía la fecha "2026-09-11" correspondiente a un viernes\n\n    Cuando se solicita calcular el próximo día hábil\n\n    Entonces el sistema debe omitir el sábado y domingo\n\n    Y debe retornar la fecha "2026-09-14" correspondiente al lunes siguiente\n  CA3: Próximo día hábil después de un viernes con lunes feriado\n\n    Dado que se envía la fecha "2026-05-22" correspondiente a un viernes\n\n    Y el lunes siguiente "2026-05-25" está configurado como día feriado\n\n    Cuando se solicita calcular el próximo día hábil\n\n    Entonces el sistema debe omitir el fin de semana y el feriado\n\n    Y debe retornar la fecha "2026-05-26" correspondiente al martes\n  CA4: Consulta realizada durante un fin de semana o feriado\n\n    Dado que se envía la fecha "2026-09-12" correspondiente a un sábado\n\n    Cuando se solicita calcular el próximo día hábil\n\n    Entonces el sistema debe retornar la fecha "2026-09-14" correspondiente al lunes siguiente hábil\n  CA5: Fecha con formato inválido o vacía\n\n    Dado que se envía un parámetro de fecha inválido como "2026-02-30" o valor nulo\n\n    Cuando se solicita calcular el próximo día hábil\n\n    Entonces el sistema debe responder con un error HTTP 400 Bad Request\n\n    Y un mensaje descriptivo indicando el formato de fecha esperado"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Calculate the next valid business day (Priority: P1)

A consumer or integrated system needs to determine the next valid work date from a supplied date so it can schedule operations only on business days. This ensures critical transactions and operational jobs run on valid dates without missing the next available opportunity.

**Why this priority**: This is the primary product capability and the foundation for all scheduling workflows. If this calculation is wrong, downstream processes may be executed on weekends or holidays.

**Independent Test**: A request for a business date, weekend date, and holiday-adjacent date can be evaluated independently and confirms that the service produces the correct next working day.

**Acceptance Scenarios**:

1. **Given** a business day such as 2026-09-07, **When** the next business day is requested, **Then** the system returns 2026-09-08.
2. **Given** a Friday such as 2026-09-11, **When** the next business day is requested, **Then** the system skips Saturday and Sunday and returns 2026-09-14.
3. **Given** a Friday followed by a configured holiday on 2026-05-25, **When** the next business day is requested, **Then** the system skips the weekend and holiday and returns 2026-05-26.
4. **Given** a weekend date such as 2026-09-12, **When** the next business day is requested, **Then** the system returns the next valid business date 2026-09-14.

---

### User Story 2 - Reject invalid or missing date input (Priority: P1)

When a caller sends a malformed or empty date, the service must fail with a clear and consistent validation response. This protects downstream operational processes from processing invalid schedules and gives callers actionable feedback.

**Why this priority**: Invalid schedule input can cause incorrect processing and operational risk. Request validation is essential before any business-day calculation is attempted.

**Independent Test**: A request with a null, empty, or malformed date can be tested alone and must return a 400 Bad Request with a descriptive message.

**Acceptance Scenarios**:

1. **Given** an invalid date such as 2026-02-30 or a null value, **When** the next business day is requested, **Then** the system responds with HTTP 400 and a message indicating the expected date format.
2. **Given** an empty date input, **When** the next business day is requested, **Then** the system responds with HTTP 400 and a clear description of the required valid date format.

---

### Edge Cases

- What happens when the supplied date is itself a holiday?
- How does the system behave when multiple consecutive days are non-working due to weekend and holiday combinations?
- What happens when the holiday calendar service is temporarily unavailable or stale?
- How does the system handle invalid time-zone or short-date input formats that are not ISO-8601?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The system MUST accept a date input representing the starting date for business-day calculation.
- **FR-002**: The system MUST determine the next valid business day by excluding weekends and configured public holidays.
- **FR-003**: The system MUST return the following calendar date when the supplied date is a regular business day and no holiday applies.
- **FR-004**: The system MUST skip Saturday and Sunday when the supplied date falls on a Friday or weekend.
- **FR-005**: The system MUST continue advancing dates until it finds the next day that is not a weekend or public holiday.
- **FR-006**: The system MUST support holiday-aware scheduling when a holiday is adjacent to a weekend or a business-day boundary.
- **FR-007**: The system MUST reject missing, null, or malformed date values with an HTTP 400 response.
- **FR-008**: The system MUST provide a descriptive validation message that indicates the expected date format when input is invalid.
- **FR-009**: The system MUST return the result as a valid date in a consistent ISO format.
- **FR-010**: The system MUST preserve the source of truth for holiday data and use the most current configured holiday calendar available to the service.

### Key Entities *(include if feature involves data)*

- **Requested Date**: The date from which the caller wants to determine the next valid business day.
- **Business Calendar**: The operational calendar that defines which dates are considered working days.
- **Holiday Calendar**: The set of configured non-working dates that must be excluded from the business-day calculation.
- **Next Business Day**: The first date after the requested date that is a valid working day for scheduling and operations.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: For all valid business-date requests, the system returns the correct next business day with no weekend or holiday drift.
- **SC-002**: Weekend scenarios and holiday-adjacent scenarios produce the expected next valid date in all acceptance cases.
- **SC-003**: Invalid date input produces an HTTP 400 response and a descriptive message in 100% of validation failures.
- **SC-004**: Operational scheduling teams can reliably identify the next valid work date before initiating transactions or processes without manual calendar checks.

## Assumptions

- The holiday calendar is available and kept current by the supporting service.
- The feature applies to the standard business week, where Saturday and Sunday are non-working days.
- Public holidays are treated as non-working dates regardless of whether they fall on a weekday or are adjacent to a weekend.
- The service uses a single date format for validation and response, aligned to ISO date standards.
- This feature is scoped to next-business-day calculation only and does not include scheduler orchestration or process execution.
