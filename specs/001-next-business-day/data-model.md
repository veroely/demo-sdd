# Data Model: Next Business Day

## Core Entities

### RequestedDate
- **Purpose**: Represents the starting date from which the next business day is calculated.
- **Fields**:
  - `date`: ISO date value in `yyyy-MM-dd` format
- **Validation rules**:
  - Must be present and non-empty
  - Must parse as a valid calendar date
  - Must not be in an invalid or ambiguous format

### HolidayCalendar
- **Purpose**: Defines dates that are excluded from business-day calculations.
- **Fields**:
  - `holidayDates`: set of non-working dates
  - `source`: origin of the holiday data (upstream service, config, or static list)
- **Validation rules**:
  - Dates must be normalized to ISO format
  - Calendar must be current enough for operational scheduling use

### NextBusinessDayResult
- **Purpose**: Represents the result returned to a consumer after the calculation is complete.
- **Fields**:
  - `inputDate`: requested date used in the calculation
  - `nextBusinessDay`: first valid working day after the input date
  - `status`: success or validation error
- **Validation rules**:
  - `nextBusinessDay` must be a valid date
  - `nextBusinessDay` must not fall on a weekend or holiday

## Relationships

- A `RequestedDate` is evaluated against a `HolidayCalendar`.
- A `HolidayCalendar` contributes to the determination of the `NextBusinessDayResult`.
- The calculation process advances from the `RequestedDate` forward until the first valid business date is found.

## State and lifecycle

- Input accepted -> validated -> date evaluated
- If date is valid: continue scanning forward day by day
- If date is invalid: return HTTP 400 with validation guidance
- If holiday calendar is unavailable: follow service-level failure handling and surface an operational error or retryable response according to platform standards
