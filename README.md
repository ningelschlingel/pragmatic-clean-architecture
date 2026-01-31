# Pragmatic Clean Architecture
Pragmatic Clean Architecture leveraging the clear data flow and error handling of Either-UseCases, while consolidating abstractions with a lean towards a loose Hexagonal approach. This improves maintainability through a flatter structure and utilizes vertical splits to isolate features for clear separation.

# What is this for?
This small project is highlighting architectual decisions in the scope of a micro-project with very minimal functionality. It is primarily built to show off the strcuture, decisions for simplification, decisions for maintainability, decisions for productivity.

For very small projects or MVPs it might already be too much. For giant systems it might lacks some level of abstraction. 

It can certainly be the starting point for a professional applicatons backend as it can grow a lot and still be managable and maintainable due to the separation of concerns with the feature split.

## Vertical Slices

Features are organized in their own vertical slice with a clear cut. Every feature is owning its data. The post-slice manages posts. The user-slice manages users. Interactions are defines with contracts in ports. Example following.
Clear boundaries for decoupling enable easy parallel work.

## Slice architecture

Inside each slice, the same exact clean/hex-structure is used:

````
├── feature/
│   ├── core/
│   │   ├── application/
│   │   │   ├── FirstUseCase.java
│   │   │   └── AnotherUseCase.java
│   │   ├── domain/
│   │   │   ├── DomainObject.java
│   │   │   ├── DomainObjectIdWrapper.java
│   │   └── port/
│   │       └── out/
│   │           ├── EntityRepository.java
│   │           ├── CrossFeatureSlicePort.java
│   └── infrastructure/
│       ├── config/
│       │   └── FeatureConfiguration.java
│       ├── persistence/
│       │   ├── JpaEntityRepository.java
│       │   ├── EntityObject.java
│       │   ├── SpringDataEntityRepository.java
│       └── web/
│           └── FeatureController.java
````

### Core

The Core defines the contract and functionality. The core does not touch anything outside of it. It uses contracts defined in interfaces and relies on the injection of the implementation.

**Application / UseCases:**. In the application folder lies the actual functionality in the form of _UseCases_. In a strict clean architecture they would implement interfaces but there will only be one implementation and in this case the abstraction was dropped as it seemed to introduce unnecessary files without adding real value. All UseCases offer their action based on vavr's `Either`-Type and define the whole contract directly within the file. A UseCase therefore holds the used ports, defines a command object if needed, defines all possible failure-types making use of sealed interfaces, defines a result-object and the necessary wrappers.

**Domain:** In the domain folder we offer value-objects, enums and constants that are required by the usecases.

**Port -> Out:** The outgoing ports define how we interact with the world outside of our feature-slice. The most standard case is interacting with a database. But within the core we don't care about _how_ this is done. For all we know, calling this method could trigger an electo-shock making a psql-expert insert the data manually. Another exmaple of a port is triggering functionality that lies outside of our feature.

### Infrastructure

In the infrastructure section, we plug everything together. While the core utilizes it's ports and the promised return-types to actually process everything, we provide the _how_ now.
Inside of this folder, there is no clear naming pattern anymore. It fully depends on what the implementation of that port-contract actually does. Commonly, there will be a persistence-folder. The web-folder, holding the spring-controllers is actually defining incoming ports that are not required to be defines elsewhere.

**Persistence:** Holds implementations of the repository-ports used for CRUD. While aiming for an pragmatic approach, a separate abstraction from the spring repositories was still valued. This allows for example, to switch out the spring repository for something else and solely changing the `JpaEntityRepository` to make use of the new solution rather than the spring way.

**Web:** Holds the spring-controllers as implementation of non separately defines ingoing ports. This is a pragmatic simplification as it would add no value.

**Config:** This is the one piece that will be part of every feature-slice as it configures the UseCase beans to use the implementations from the infrastucture layer. We could also use the `@Service`-Annotation in our UseCases but generally we want to keep framework-specific functionality out of the core.
