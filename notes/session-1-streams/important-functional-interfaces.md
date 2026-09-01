# Important Functional Interfaces in Java

## 1. `Predicate<T>`

**Purpose:** Test a condition → returns `boolean`

### Method

```java
boolean test(T t);
```

### Use cases

* Filtering collections
* Conditional checks
* Validation logic

### Example

```java
Predicate<Integer> isEven = n -> n % 2 == 0;

System.out.println(isEven.test(10)); // true
```

### Real-world Stream usage

```java
List<Integer> numbers = List.of(1,2,3,4,5,6);

numbers.stream()
       .filter(n -> n > 3)
       .forEach(System.out::println);
```

Think of `Predicate` as **IF condition as an object**.

---

## 2. `Function<T, R>`

**Purpose:** Transform one value into another

### Method

```java
R apply(T t);
```

### Use cases

* Mapping data
* Converting DTO → Entity
* Extracting fields

### Example

```java
Function<String, Integer> lengthFn = s -> s.length();

System.out.println(lengthFn.apply("Java")); // 4
```

### Stream usage

```java
List<String> names = List.of("Ram", "Shyam", "Amit");

names.stream()
     .map(String::length)
     .forEach(System.out::println);
```

Think of `Function` as **input → output transformation**.

---

## 3. `Consumer<T>`

**Purpose:** Perform an action, no return value

### Method

```java
void accept(T t);
```

### Use cases

* Logging
* Printing
* Sending messages
* Saving to DB

### Example

```java
Consumer<String> printer = s -> System.out.println(s);

printer.accept("Hello Java");
```

### Stream usage

```java
names.forEach(name -> System.out.println(name));
```

Think of `Consumer` as **do something with data**.

---

## 4. `Supplier<T>`

**Purpose:** Supply or generate values (no input)

### Method

```java
T get();
```

### Use cases

* Lazy initialization
* Generating objects
* Default values
* Factory patterns

### Example

```java
Supplier<Double> randomSupplier = () -> Math.random();

System.out.println(randomSupplier.get());
```

### Practical example

```java
Supplier<List<String>> listSupplier = ArrayList::new;
```

Think of `Supplier` as **give me something when I ask**.

---

## 5. `BiPredicate<T, U>`

**Purpose:** Condition check with two inputs

### Method

```java
boolean test(T t, U u);
```

### Example

```java
BiPredicate<String, String> startsWith =
        (str, prefix) -> str.startsWith(prefix);

System.out.println(startsWith.test("Java", "Ja")); // true
```

### Use case

* Comparing two values
* Authorization rules
* Validation logic

---

## 6. `BiFunction<T, U, R>`

**Purpose:** Two inputs → one output

### Method

```java
R apply(T t, U u);
```

### Example

```java
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

System.out.println(add.apply(10, 20)); // 30
```

### Use cases

* Calculations
* Merging objects
* Combining data

---

## 7. `UnaryOperator<T>`

**Purpose:** Special `Function` where input and output are same type

### Method

```java
T apply(T t);
```

### Example

```java
UnaryOperator<Integer> square = x -> x * x;

System.out.println(square.apply(5)); // 25
```

### Use cases

* Updating values
* Incrementing, modifying same object

---

## 8. `BinaryOperator<T>`

**Purpose:** Special `BiFunction` where both inputs and output are same type

### Method

```java
T apply(T t1, T t2);
```

### Example

```java
BinaryOperator<Integer> max = Integer::max;

System.out.println(max.apply(10, 20)); // 20
```

### Stream usage

```java
int sum = numbers.stream()
                 .reduce(0, Integer::sum);
```

---

## 9. `Runnable`

**Purpose:** Execute code in a separate thread

### Method

```java
void run();
```

### Example

```java
Runnable task = () -> System.out.println("Running in a thread");

new Thread(task).start();
```

Still very important for **multithreading**.

---

## 10. `Callable<V>`

**Purpose:** Like `Runnable` but **returns value and throws exception**

### Method

```java
V call() throws Exception;
```

### Example

```java
Callable<Integer> task = () -> 10 + 20;
```

Used heavily with **ExecutorService**.

---

## Quick Mental Model (Very Important)

| Interface      | Think of it as          |
| -------------- | ----------------------- |
| Predicate      | IF condition            |
| Function       | Data transformer        |
| Consumer       | Do something            |
| Supplier       | Give me data            |
| UnaryOperator  | Modify same type        |
| BinaryOperator | Combine same type       |
| Runnable       | Run code                |
| Callable       | Run code + return value |

---
## Summary

> **Streams are nothing without functional interfaces.**

* `filter` → `Predicate`
* `map` → `Function`
* `forEach` → `Consumer`
* `reduce` → `BinaryOperator`

