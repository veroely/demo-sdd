# Contract: Next Business Day API

## Endpoint

`GET /api/business-days/next?date={yyyy-MM-dd}`

## Purpose

Returns the first valid business day after the provided date, excluding weekends and configured non-working dates.

## Request

- Query parameter: `date`
- Required: yes
- Format: ISO date as `yyyy-MM-dd`

## Success response

**HTTP Status**: `200 OK`

```json
{
  "inputDate": "2026-09-07",
  "nextBusinessDay": "2026-09-08"
}
```

## Validation error response

**HTTP Status**: `400 Bad Request`

```json
{
  "error": "INVALID_DATE",
  "message": "Date must be provided in ISO-8601 format (yyyy-MM-dd)."
}
```

## Behavioral rules

- Weekends are excluded automatically.
- Configured holiday dates are excluded automatically.
- The algorithm returns the first date strictly after the input date that is valid for operations.
- The response format remains consistent across all valid inputs.
