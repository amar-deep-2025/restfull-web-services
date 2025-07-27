# 📡 RESTful Web Services with Spring Boot

A RESTful web service project built using **Java** and **Spring Boot**, integrated with **Talend APIs** for seamless data integration and processing. This project exposes clean, scalable, and secure REST endpoints for data operations, backed by **MySQL**. It is tested using **Postman** and designed for **real-time API consumption**.

---

## ✅ Features

- Clean RESTful endpoints using Spring Boot
- Talend API integration
- Real-time data processing
- MySQL database backend
- Swagger UI for interactive API testing
- Validation and Exception Handling
- Content Negotiation (JSON/XML)
- Internationalization (i18n)
- API Versioning
- HATEOAS support
- Static & Dynamic Response Filtering

---

## 📡 Response Status Codes

| Code | Meaning                 | Usage                                    |
|------|-------------------------|------------------------------------------|
| 200  | OK                      | Successful response                      |
| 201  | Created                 | Resource created successfully            |
| 204  | No Content              | Deleted successfully, no content to show|
| 400  | Bad Request             | Validation or client-side error          |
| 401  | Unauthorized            | Authentication failed                    |
| 404  | Not Found               | Resource not found                       |
| 500  | Internal Server Error   | Server-side issue                        |

**Use `ResponseEntity<T>`** to return status code + response body.

---

## 🔁 Resource Location Header

When creating a new resource (e.g., via POST), return the **URI of the new resource** using the `Location` header.

```java
URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}")
    .buildAndExpand(savedUser.getId())
    .toUri();

return ResponseEntity.created(location).build();
