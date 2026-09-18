# Microservicio Next Business Day

Microservicio REST desarrollado con Spring Boot que calcula el siguiente día hábil posterior a una fecha. No cuenta sábados, domingos ni fechas configuradas como feriados.

## Requisitos

- Java 21
- Gradle Wrapper incluido en el proyecto
- Puerto `8080` disponible

Verifica la versión de Java:

```bash
java -version
```

## Levantar el microservicio

Desde la raíz del proyecto, ejecuta:

### Windows

```powershell
.\gradlew.bat bootRun
```

### Linux o macOS

```bash
./gradlew bootRun
```

La aplicación quedará disponible en:

```text
http://localhost:8080
```

También puedes generar el ejecutable y levantarlo con Java:

```bash
./gradlew build
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

En Windows, el primer comando equivalente es:

```powershell
.\gradlew.bat build
java -jar build\libs\demo-0.0.1-SNAPSHOT.jar
```

## Ejecutar las pruebas

Linux o macOS:

```bash
./gradlew test
```

Windows:

```powershell
.\gradlew.bat test
```

## Consumir la API

### Obtener el siguiente día hábil

**Método:** `GET`  
**Ruta:** `/api/business-days/next`  
**Parámetro requerido:** `date` en formato ISO `yyyy-MM-dd`

Ejemplo con `curl`:

```bash
curl "http://localhost:8080/api/business-days/next?date=2026-09-07"
```

Respuesta exitosa (`200 OK`):

```json
{
  "inputDate": "2026-09-07",
  "nextBusinessDay": "2026-09-08"
}
```

La fecha de entrada no se incluye: el servicio siempre busca una fecha posterior. Por ejemplo, si la fecha recibida es un viernes, la respuesta será el lunes siguiente, salvo que sea un feriado configurado.

### Ejemplo de consumo desde PowerShell

```powershell
Invoke-RestMethod -Method Get `
  -Uri "http://localhost:8080/api/business-days/next?date=2026-09-07"
```

### Error de validación

La fecha es obligatoria y debe ser válida y estar en formato `yyyy-MM-dd`.

Ejemplo:

```bash
curl -i "http://localhost:8080/api/business-days/next?date=2026-02-30"
```

Respuesta (`400 Bad Request`):

```json
{
  "error": "INVALID_DATE",
  "message": "Date must be provided in ISO-8601 format (yyyy-MM-dd)."
}
```

El mismo error se devuelve cuando el parámetro `date` no se envía o se envía vacío.

## Reglas y configuración actual

- Se excluyen sábados y domingos.
- El feriado configurado actualmente es `2026-05-25`.
- El calendario de feriados se encuentra en `ConfiguredHolidayCalendar` y puede ampliarse según las necesidades del negocio.

## Estructura de la API

```text
GET /api/business-days/next?date={yyyy-MM-dd}
```

No se requiere autenticación ni cuerpo JSON para consumir este endpoint.