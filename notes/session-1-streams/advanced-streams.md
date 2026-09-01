
# Stream Grouping, Partitioning & Collectors to Map

## 1. `groupingBy()` – Group elements by a key (MOST IMPORTANT)

### What it does

Groups elements into a `Map<K, List<T>>` based on a classifier.

---

### Basic Syntax

```java
Collectors.groupingBy(classifier)
```

---

### Example 1: Group Strings by Length

```java
List<String> names = List.of("Ram", "Shyam", "Amit", "John");

Map<Integer, List<String>> result =
    names.stream()
         .collect(Collectors.groupingBy(String::length));
```

### Output

```text
{
  3 = [Ram],
  4 = [Amit, John],
  5 = [Shyam]
}
```

---

### When to use

* Categorization
* Bucketing data
* Reports & dashboards
* Aggregations

---

## 2. `groupingBy()` with Downstream Collectors (VERY IMPORTANT)

### Problem

You don’t always want `List<T>` as value.

---

### Example 2: Group and Count

```java
Map<Integer, Long> countByLength =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length,
             Collectors.counting()
         ));
```

### Output

```text
{3=1, 4=2, 5=1}
```

---

### Example 3: Group and Map Values

```java
Map<Integer, List<String>> upperCaseNames =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length,
             Collectors.mapping(String::toUpperCase, Collectors.toList())
         ));
```

---

### Example 4: Group and Find Max

```java
Map<Integer, Optional<String>> longestNameByLength =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length,
             Collectors.maxBy(Comparator.naturalOrder())
         ));
```

---

## 3. Multi-level Grouping (COMMON IN REAL SYSTEMS)

### Example: Group by Length → Then First Character

```java
Map<Integer, Map<Character, List<String>>> result =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length,
             Collectors.groupingBy(name -> name.charAt(0))
         ));
```

### Use cases

* Hierarchical reports
* Analytics
* Nested dashboards

---

## 4. Custom Map Implementation with `groupingBy`

### Default

* Uses `HashMap`

### Example: Use `TreeMap`

```java
Map<Integer, List<String>> treeMap =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length,
             TreeMap::new,
             Collectors.toList()
         ));
```

---

## 5. `partitioningBy()` – Special case of grouping (BOOLEAN ONLY)

### What it does

Splits data into **exactly two groups**: `true` and `false`

---

### Syntax

```java
Collectors.partitioningBy(predicate)
```

---

### Example 1: Even vs Odd

```java
Map<Boolean, List<Integer>> result =
    numbers.stream()
           .collect(Collectors.partitioningBy(n -> n % 2 == 0));
```

### Output

```text
{
  true = [2,4,6],
  false = [1,3,5]
}
```

---

### Difference: `groupingBy` vs `partitioningBy`

| Feature  | groupingBy     | partitioningBy  |
| -------- | -------------- | --------------- |
| Keys     | Any type       | Boolean only    |
| Groups   | Dynamic        | Always 2        |
| Use case | Categorization | Yes/No decision |

---

### Example 2: Partition + Count

```java
Map<Boolean, Long> count =
    numbers.stream()
           .collect(Collectors.partitioningBy(
               n -> n > 3,
               Collectors.counting()
           ));
```

---

## 6. Collectors to `Map` – `toMap()` (VERY COMMON)

### What it does

Converts stream into `Map<K, V>`

---

### Basic Syntax

```java
Collectors.toMap(keyMapper, valueMapper)
```

---

### Example 1: Name → Length

```java
Map<String, Integer> map =
    names.stream()
         .collect(Collectors.toMap(
             name -> name,
             String::length
         ));
```

---

## 7. Handling Duplicate Keys in `toMap()` (INTERVIEW FAVORITE)

### Problem

```java
List<String> names = List.of("Ram", "Amit", "Ram");
```

This throws:

```
IllegalStateException: Duplicate key
```

---

### Solution: Merge Function

```java
Map<String, Integer> map =
    names.stream()
         .collect(Collectors.toMap(
             name -> name,
             String::length,
             (oldVal, newVal) -> oldVal   // keep existing
         ));
```

---

### Example: Pick Longer Name

```java
Collectors.toMap(
    name -> name,
    String::length,
    Integer::max
)
```

---

## 8. `toMap()` with Custom Map Implementation

```java
Map<String, Integer> treeMap =
    names.stream()
         .collect(Collectors.toMap(
             name -> name,
             String::length,
             (a, b) -> a,
             TreeMap::new
         ));
```

---

## 9. Grouping vs `toMap()` – When to Use What

| Scenario              | Use                       |
| --------------------- | ------------------------- |
| One key → many values | `groupingBy`              |
| One key → one value   | `toMap`                   |
| Boolean split         | `partitioningBy`          |
| Aggregation           | `groupingBy + downstream` |

---

## 10. Real-World Microservice Example

### Scenario

Orders → Group by customer → total amount

```java
Map<Long, Double> totalAmountByCustomer =
    orders.stream()
          .collect(Collectors.groupingBy(
              Order::getCustomerId,
              Collectors.summingDouble(Order::getAmount)
          ));
```

---

### Partition orders into high-value and normal

```java
Map<Boolean, List<Order>> result =
    orders.stream()
          .collect(Collectors.partitioningBy(
              o -> o.getAmount() > 10_000
          ));
```

---

## 11. Collector Cheat Sheet

| Collector           | Purpose          |
| ------------------- | ---------------- |
| groupingBy          | Group data       |
| partitioningBy      | Boolean split    |
| mapping             | Transform values |
| counting            | Count elements   |
| summingInt / Double | Sum              |
| averaging           | Average          |
| maxBy / minBy       | Find extremes    |
| toList / toSet      | Collect          |



