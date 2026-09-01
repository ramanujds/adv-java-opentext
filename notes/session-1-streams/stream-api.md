
# Java Stream – Important Methods 

> **Stream = declarative way to process collections (filter, transform, aggregate)**

---

## 1. `filter()` – Select data (MOST USED)

### What it does

Filters elements based on a condition.

### Takes

`Predicate<T>`

### Example

```java
List<Integer> numbers = List.of(1,2,3,4,5,6);

numbers.stream()
       .filter(n -> n % 2 == 0)
       .forEach(System.out::println);
```

### Use cases

* Remove unwanted data
* Apply business rules
* Conditional selection

---

## 2. `map()` – Transform data (MOST USED)

### What it does

Transforms each element into another form.

### Takes

`Function<T, R>`

### Example

```java
List<String> names = List.of("Ram", "Shyam");

names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

### Use cases

* DTO → Entity
* Extract fields
* Convert data types

---

## 3. `flatMap()` – Flatten nested structures (IMPORTANT)

### What it does

Flattens multiple streams into one.

### Example

```java
List<List<String>> data = List.of(
    List.of("A", "B"),
    List.of("C", "D")
);

data.stream()
    .flatMap(List::stream)
    .forEach(System.out::println);
```

### Use cases

* `List<List<T>>`
* One-to-many relationships
* Nested collections

---

## 4. `forEach()` – Perform action

### What it does

Consumes each element (terminal operation).

### Takes

`Consumer<T>`

### Example

```java
numbers.stream()
       .forEach(System.out::println);
```

### Use cases

* Logging
* Printing
* Calling external services

Avoid modifying shared state inside `forEach`.

---

## 5. `collect()` – Convert stream to collection (VERY IMPORTANT)

### What it does

Collects stream into a `List`, `Set`, `Map`, etc.

### Example: List

```java
List<Integer> evenNumbers =
    numbers.stream()
           .filter(n -> n % 2 == 0)
           .collect(Collectors.toList());
```

### Example: Map

```java
Map<Integer, String> map =
    names.stream()
         .collect(Collectors.toMap(
             String::length,
             name -> name
         ));
```

---

## 6. `reduce()` – Aggregate into single value

### What it does

Combines elements into one result.

### Example: Sum

```java
int sum = numbers.stream()
                 .reduce(0, Integer::sum);
```

### Example: Max

```java
int max = numbers.stream()
                 .reduce(Integer::max)
                 .orElse(0);
```

### Use cases

* Sum, max, min
* Aggregation logic

---

## 7. `sorted()` – Sort elements

### Natural order

```java
numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

### Custom order

```java
names.stream()
     .sorted((a, b) -> b.compareTo(a))
     .forEach(System.out::println);
```

---

## 8. `distinct()` – Remove duplicates

### Example

```java
List<Integer> nums = List.of(1,2,2,3,3,4);

nums.stream()
    .distinct()
    .forEach(System.out::println);
```

Uses `equals()` and `hashCode()`.

---

## 9. `limit()` and `skip()` – Control size

### `limit(n)`

```java
numbers.stream()
       .limit(3)
       .forEach(System.out::println);
```

### `skip(n)`

```java
numbers.stream()
       .skip(2)
       .forEach(System.out::println);
```

### Use cases

* Pagination
* Top-N results

---

## 10. `anyMatch()`, `allMatch()`, `noneMatch()`

### anyMatch

```java
boolean hasEven =
    numbers.stream()
           .anyMatch(n -> n % 2 == 0);
```

### allMatch

```java
boolean allPositive =
    numbers.stream()
           .allMatch(n -> n > 0);
```

### noneMatch

```java
boolean noneNegative =
    numbers.stream()
           .noneMatch(n -> n < 0);
```

---

## 11. `findFirst()` and `findAny()`

### findFirst

```java
Optional<Integer> first =
    numbers.stream().findFirst();
```

### findAny (useful in parallel streams)

```java
Optional<Integer> any =
    numbers.stream().findAny();
```

---

## 12. `count()` – Count elements

```java
long count =
    numbers.stream()
           .filter(n -> n > 3)
           .count();
```

---

## 13. `peek()` – Debugging only

### What it does

Allows inspecting elements **without modifying them**.

```java
numbers.stream()
       .peek(n -> System.out.println("Before: " + n))
       .map(n -> n * 2)
       .peek(n -> System.out.println("After: " + n))
       .toList();
```

Don’t use for business logic.

---

## 14. `parallelStream()` – Parallel processing

```java
numbers.parallelStream()
       .filter(n -> n % 2 == 0)
       .forEach(System.out::println);
```

Use carefully:

* CPU-intensive tasks
* No shared mutable state

---

## Stream Pipeline Flow (INTERVIEW GOLD)

```java
collection.stream()        // source
          .filter()        // intermediate
          .map()           // intermediate
          .sorted()        // intermediate
          .collect();      // terminal
```

* **Intermediate methods** → lazy
* **Terminal method** → triggers execution

---

## Most Important Methods (If You Remember Only 6)

1. `filter`
2. `map`
3. `flatMap`
4. `collect`
5. `reduce`
6. `forEach`

Master these and you can solve **80% stream problems**.

---

## Real-World Microservice Example

```java
orders.stream()
      .filter(o -> o.getAmount() > 1000)
      .map(Order::getCustomerId)
      .distinct()
      .sorted()
      .limit(10)
      .toList();
```

Readable. Declarative. Production-ready.

---


