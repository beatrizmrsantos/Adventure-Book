# Adventure Book

Monorepo with backend (Spring Boot) and frontend (Angular).

## Structure

```
Adventure Book/
├── backend/    # Spring Boot (Java 21, Maven)
└── frontend/   # Angular (standalone components, SCSS)
```

## Requirements

- Java 21
- Node.js + npm
- Angular CLI (`npm install -g @angular/cli`)

## How to run

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

Available at: http://localhost:8080

### Frontend

In another terminal:

```bash
cd frontend
ng serve
```

Available at: http://localhost:4200
