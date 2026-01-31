# Pragmatic Clean Architecture

This project provides a streamlined blueprint for Spring Boot applications, balancing **Clean Architecture** principles with **Development Productivity**. It demonstrates a "loose" Hexagonal approach that prioritizes code maintainability and clear data flow without the overhead of excessive abstractions.

---

## 🎯 Project Goals

* **Maintainability:** Features are isolated into vertical slices to prevent "spaghetti" dependencies.
* **Clarity:** Business logic is decoupled from technical details like Spring, JPA, or Web controllers.
* **Productivity:** Minimal boilerplate; abstractions (like interfaces) are only utilized where they provide tangible value for testing or decoupling.

---

## 🧱 Architectural Model: Vertical Slices

Rather than grouping by technical layers (e.g., all Controllers in one package), this project uses **Vertical Slices**. Each package represents a standalone feature that owns its logic and data.

**Data Flow Overview:**
```text
  [ Web / UI ] 
       |
       v
 [ UseCase (Core) ] 
       |
       v
 [ Port (Interface) ] <--- [ Persistence Adapter (Infra) ]
 ```
---

## 📂 Slice Anatomy

Every feature slice is divided into two main areas: **Core** and **Infrastructure**.

### 1. Core (Business Logic)
The Core is the heart of the application. It is framework-agnostic and contains no references to Spring or JPA.
* **Application / UseCases:** Single-responsibility classes implementing business logic. They return Vavr `Either<Failure, Success>` to make error handling explicit and type-safe.
* **Domain:** Contains domain entities, value objects (e.g., `PostId`), and enums.
* **Port -> Out:** Outgoing interfaces (contracts) for external communication, such as database access or calling another feature slice.

### 2. Infrastructure (Technical Details)
The Infrastructure layer provides the implementation for the Core's contracts.
* **Persistence:** Implements the `Port` interfaces. Conversion between Domain models and JPA Entities happens **privately** within the `JpaRepository` implementation to prevent database annotations from leaking into the Core.
* **Web:** Holds Spring REST Controllers. These act as entry points, mapping HTTP requests to UseCase commands.
* **Config:** Contains `@Configuration` classes to wire UseCases as Spring beans. This keeps the Core free of framework-specific annotations like `@Service`.

---

## 🛠️ Key Technical Decisions

| Decision | Rationale |
| :--- | :--- |
| **No UseCase Interfaces** | Since most UseCases have only one implementation, we skip the interface to reduce file bloat. |
| **Vavr `Either`** | Replaces runtime exceptions with explicit return types, forcing compile-time error handling. |
| **Internal Mapping** | Mapping logic is encapsulated within the Persistence Adapter. This keeps the Core "pure" and the schema details hidden. |
| **Manual Bean Wiring** | UseCases are instantiated in `@Configuration` classes to ensure the Core remains 100% framework-independent. |

---

## 🚦 Dependency Rules

1. **Inward Only:** The `Infrastructure` layer depends on the `Core`. The `Core` must never depend on the `Infrastructure`.
2. **No Cross-Slice Leaking:** One slice cannot access the internal JPA entities or infrastructure of another slice. Communication must occur via defined `Ports`.
3. **Shared Package:** Global concerns (like `UserId` or `AuthenticatedUser`) reside in a `shared` package to maintain consistency without duplicating core logic.

---

## 🚀 Getting Started

1. **Explore a Slice:** Examine `src/main/java/.../post` to see a full implementation of UseCases, Domain models, and JPA Adapters.
2. **Run the Project:** Standard Spring Boot execution via `./gradlew bootRun`.
3. **Core Testing:** Because the Core is framework-free, you can write high-speed unit tests for business logic without the need for `@SpringBootTest`.

---

## ⚠️ Important Disclaimers
1. No Test Coverage: This repository is strictly an architectural showcase. Tests have been intentionally stripped or omitted. In a production environment, the Core logic should be covered by JUnit tests, and Infrastructure by Integration tests (e.g., Testcontainers).

2. Mock Functionality: The logic within the UseCases is minimal and serves only as a placeholder to demonstrate data flow. Do not use the business logic itself as a reference for production-grade feature implementation.

3. Scope: This setup is optimized for mid-to-large projects. For tiny MVPs, this level of separation might be over-engineered; for massive distributed systems, you may require additional layers of abstraction (like dedicated DTOs for every boundary).
