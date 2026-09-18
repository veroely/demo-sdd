# Research: Next Business Day

## Decisions

### Decision: Use ISO-8601 dates with strict validation
- The service will accept dates in `yyyy-MM-dd` format and reject values that are missing, empty, or not a valid calendar date.
- Rationale: This matches the acceptance criteria and prevents schedule errors caused by malformed input.
- Alternatives considered: permissive parsing and locale-based formats; rejected because they create ambiguity and inconsistent operational behavior.

### Decision: Exclude weekends and configured holidays from the calculation
- The algorithm advances one day at a time until it finds a date that is neither a Saturday/Sunday nor a configured holiday.
- Rationale: This is the simplest and most testable approach and directly matches the acceptance scenarios.
- Alternatives considered: skip-ahead logic based on weeks or configurable workday offsets; rejected because they are more error-prone and less explicit than a straightforward date scan.

### Decision: Keep v1 as a deterministic service with external holiday source
- The system will rely on a configured holiday calendar provided by an upstream service or static configuration data.
- Rationale: The feature definition requires a current holiday calendar and assumes the upstream service is available and updated.
- Alternatives considered: embedded holiday rules only; rejected because the feature explicitly depends on the holiday calendar being maintained externally.

### Decision: Return clearly structured failure responses
- Invalid inputs must return an HTTP 400 response with a descriptive validation message.
- Rationale: This ensures external consumers can react predictably and avoids silent processing of bad dates.
- Alternatives considered: returning a null value or generic 500; rejected because they reduce operability and violate the acceptance criteria.

## Open assumptions captured from spec

- The holiday calendar is available and current.
- The business week is Monday through Friday, with Saturday and Sunday excluded.
- Public holidays are treated as non-working days and may occur adjacent to weekends.
- The feature scope is limited to determining the next valid business day and does not include orchestration logic.

## Best-practice findings

- Use `java.time.LocalDate` for date comparisons and validation because it is immutable, clear, and suitable for ISO date handling.
- Validate before calculation so malformed input fails fast with a predictable API error.
- Keep the holiday check isolated in a dedicated service or repository boundary to preserve testability and support future calendar-source changes.
