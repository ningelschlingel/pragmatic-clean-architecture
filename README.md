# pragmatic-clean-architecture
Pragmatic Clean Architecture leveraging the clear data flow and error handling of Either-UseCases, while consolidating abstractions with a lean towards a loose Hexagonal approach. This improves maintainability through a flatter structure and utilizes vertical splits to isolate features for clear separation.

High level: Vertical slices per feature

Per feature: Mix of clean and hex architecture being more explicit where it provides value (more clean) and pragmatic where abstractions and folders don't provide any value.

Usecases: vavr either structure with specific per-usecase failures for explicit handling trough the whole application
