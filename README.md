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

## 📦 Self-Contained Contracts & Localized Mapping

This project favors **locality of reference**. We consolidate contracts and translation logic exactly where they are used, but only when the complexity justifies it.

#### 1. The "As-Needed" UseCase Contract
Every UseCase acts as the "Single Source of Truth" for its specific action, but we avoid "ghost" abstractions:
* **No Artificial Wrappers:** If a UseCase only requires a single identifier (e.g., `PostId`), we pass it directly. We only define a nested `Command` record for complex, multi-parameter inputs.
* **Flexible Outputs:** We only define a nested `Result` record if the operation needs to return data. For "fire and forget" operations, we simply use `Either<Failure, Void>`.
* **Sealed Failures:** We define a nested `sealed interface` for business errors. This allows the Web layer to use exhaustive `switch` expressions, ensuring every failure case is handled at compile-time.
* **Internal Mapping:** Private methods within the UseCase handle the mapping from `Command` to `Domain Entity`, keeping the logic self-contained.

#### 2. Localized Web Mapping
In the `Web` layer, `Request` and `Response` DTOs are defined as private records directly within the Controller.
* **No Global DTOs:** API-specific structures live where they are used. This prevents a "DTO Junk Drawer" where objects are reused and leaked across unrelated endpoints.
* **Direct Mapping:** The Controller is responsible for transforming its private DTOs into UseCase inputs. This keeps the Core completely unaware of the Web/JSON contract.

**Benefit:** You can understand the entire business rule and its external interface by reading one or two files from top to bottom, rather than jumping between five different packages.

---

## 🛠️ Key Technical Decisions

| Decision | Rationale |
| :--- | :--- |
| **Consolidated Contracts** | Command, Result, and Failure types are nested inside the UseCase. This keeps the "API" of the business logic highly discoverable. |
| **Pragmatic Transactions** | UseCases utilize `@Transactional` to ensure atomicity. We accept this Spring dependency in the Core to gain robust transaction propagation. |
| **No UseCase Interfaces** | Since most UseCases have only one implementation, we skip the interface to reduce file bloat. |
| **Vavr `Either`** | Replaces runtime exceptions with explicit return types, forcing compile-time error handling. |
| **Internal Mapping** | Mapping logic (DTO -> Command, Command -> Domain) is kept private within the classes. This prevents "Mapper-Class Bloat" and keeps translation logic close to the data it processes. |
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
