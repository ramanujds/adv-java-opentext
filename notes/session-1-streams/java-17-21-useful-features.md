# JDK 17 – Important & Useful Features (LTS)


## 1. Sealed Classes (VERY IMPORTANT)

### What problem it solves

Controls **which classes are allowed to extend or implement a type**.

Before:

* Anyone could extend your class/interface
* Hard to model closed hierarchies

---

### Example

```java
public sealed interface Payment
        permits CardPayment, UpiPayment, CashPayment {
}
```

```java
public final class CardPayment implements Payment {}
public final class UpiPayment implements Payment {}
public final class CashPayment implements Payment {}
```

### Why it matters

* Strong domain modeling
* Safer inheritance
* Works beautifully with `switch` (JDK 21)

---

## 2. Pattern Matching for `instanceof`

### Before

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

### JDK 17

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### Why it matters

* Less boilerplate
* Safer casting
* Cleaner business logic

---

## 3. Records

### What problem it solves

DTOs were verbose and error-prone.

---

### Example

```java
public record User(Long id, String name, String email) {}
```

Auto-generates:

* constructor
* getters
* `equals`, `hashCode`
* `toString`

### Why it matters

* Perfect for DTOs, API models, events
* Immutable by default
* Excellent for microservices

---

## 4. Text Blocks (Production-Ready)

### Before

```java
String json = "{ \"name\": \"Ram\", \"age\": 30 }";
```

### JDK 17

```java
String json = """
    {
      "name": "Ram",
      "age": 30
    }
    """;
```

### Use cases

* JSON
* SQL
* GraphQL
* HTML templates

---

## 5. Helpful NullPointerExceptions

### Before

```text
NullPointerException
```

### JDK 17

```text
Cannot invoke "user.getName()" because "user" is null
```

### Why it matters

* Faster debugging
* Less guesswork

---

# JDK 21 – Important & Useful Features (LTS)

## 1. Virtual Threads (GAME CHANGER)

### Problem with traditional threads

* Threads are expensive
* Limited scalability
* Blocking = bad

---

### JDK 21 Solution

**Virtual Threads** → lightweight threads managed by JVM

---

### Example

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> handleRequest());
}
```

### Why it matters (BIG)

* Millions of concurrent requests
* Simple blocking code
* No reactive complexity
* Perfect for microservices

Think:

> **Write blocking code, get non-blocking scalability**

---

## 2. Pattern Matching for `switch` (VERY IMPORTANT)

### Before

```java
if (obj instanceof String s) {
    ...
} else if (obj instanceof Integer i) {
    ...
}
```

### JDK 21

```java
switch (obj) {
    case String s -> System.out.println(s.length());
    case Integer i -> System.out.println(i * 2);
    case null -> System.out.println("null");
    default -> System.out.println("unknown");
}
```

### Why it matters

* Cleaner logic
* Works perfectly with sealed classes
* Eliminates long `if-else`

---

## 3. Record Patterns

### Example

```java
record Point(int x, int y) {}

if (obj instanceof Point(int x, int y)) {
    System.out.println(x + ", " + y);
}
```

### Combined with switch

```java
switch (obj) {
    case Point(int x, int y) -> System.out.println(x + y);
    default -> {}
}
```

Very powerful for domain modeling.

---

## 4. Sequenced Collections

### Problem

No standard way to get first/last element.

### JDK 21

```java
SequencedCollection<String> list = new ArrayList<>();

list.addFirst("A");
list.addLast("B");

String first = list.getFirst();
String last = list.getLast();
```

### Why it matters

* Cleaner APIs
* Less error-prone indexing

---

## 6. String Templates (Preview, but Important)

### Example

```java
String name = "Ram";
int age = 30;

String result = STR."Name: \{name}, Age: \{age}";
```

### Why it matters

* Safer than concatenation
* Cleaner formatting
* Will replace messy `String.format`

---

# What Should You Use in Real Projects?

### If you’re on Java 8/11

→ **Move to JDK 17 immediately**

### If starting new project / upgrading infra

→ **JDK 21 is the future**

### Use These Features Actively
* Sealed Classes
* Records
* Virtual Threads
* Pattern Matching
* Text Blocks
* String Templates
