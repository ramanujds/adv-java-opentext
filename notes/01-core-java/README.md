# Core Java (Java 17/21 LTS)

Notes and runnable-style examples for the first section of the course. Each
topic gets its own file with a **before → after** comparison, a diagram, and
an honest discussion of what actually improved (and what didn't).

## Topics

1. [Java 8 refresher — Streams, Optionals, Lambdas](01-java8-refresher.md)
2. [Records, sealed classes, pattern matching](02-records-sealed-classes-pattern-matching.md)
3. [Lambda expressions & functional interfaces](03-lambda-expressions-functional-interfaces.md)
4. [Stream API — Collectors, parallel streams](04-stream-api-collectors-parallel-streams.md)
5. [Virtual threads (Project Loom) & structured concurrency](05-virtual-threads-structured-concurrency.md)
6. [Text blocks, var, enhanced NIO](06-text-blocks-var-enhanced-nio.md)

## How the language evolved

```mermaid
timeline
    title Core Java feature timeline
    2014 (Java 8)  : Lambdas
                    : Stream API
                    : Optional
                    : Default methods
    2017 (Java 9)  : java.nio.file.Files convenience methods
                    : Collection factory methods (List.of)
    2018 (Java 10) : var (local-variable type inference)
    2021 (Java 17 LTS) : Sealed classes
                        : Pattern matching for instanceof
                        : Text blocks
    2023 (Java 21 LTS) : Record patterns
                        : Pattern matching for switch
                        : Virtual threads
                        : Structured concurrency (preview)
```

## Reading order

If you only have limited time, read them in this order — each one builds on
concepts from the last:

```mermaid
flowchart LR
    A["1. Java 8 refresher"] --> B["3. Lambdas & functional interfaces"]
    B --> C["4. Stream API"]
    A --> D["2. Records, sealed, pattern matching"]
    D --> C
    C --> E["5. Virtual threads & structured concurrency"]
    A --> F["6. Text blocks, var, NIO"]
```
