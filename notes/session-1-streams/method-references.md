# Method References vs Lambdas


### Lambda Expression

A **lambda** is an **inline anonymous function**.

```java
x -> x * x
```

You write the **logic right there**.

---

### Method Reference

A **method reference** is a **shorthand for a lambda that already exists as a method**.

```java
Math::square   // conceptually
```

You **refer to existing behavior**, you don’t write new logic.

---

## Core Difference 

> **Lambda = write behavior**
> **Method Reference = reuse behavior**

---

## Basic Comparison Example

### Lambda

```java
list.forEach(x -> System.out.println(x));
```

### Method Reference

```java
list.forEach(System.out::println);
```

✔ Same result
✔ Method reference is cleaner

---

## Types of Method References (IMPORTANT)

### 1. Static Method Reference

```java
ClassName::staticMethod
```

#### Lambda

```java
(x, y) -> Math.max(x, y)
```

#### Method Reference

```java
Math::max
```

Used in:

```java
numbers.stream().reduce(Math::max);
```

---

### 2. Instance Method of a Particular Object

```java
instance::method
```

#### Lambda

```java
x -> logger.log(x)
```

#### Method Reference

```java
logger::log
```

---

### 3. Instance Method of an Arbitrary Object

```java
ClassName::method
```

#### Lambda

```java
(s1, s2) -> s1.compareTo(s2)
```

#### Method Reference

```java
String::compareTo
```

Very common in sorting:

```java
list.sort(String::compareTo);
```

---

### 4. Constructor Reference

```java
ClassName::new
```

#### Lambda

```java
() -> new ArrayList<>()
```

#### Method Reference

```java
ArrayList::new
```

Used in:

```java
Supplier<List<String>> supplier = ArrayList::new;
```

---

## When Lambda is Better

### 1. Custom Logic

```java
numbers.stream()
       .filter(n -> n % 2 == 0 && n > 10);
```

You **can’t** write this as a method reference easily.

---

### 2. Multi-step Logic

```java
x -> {
    if (x < 0) return 0;
    return x * 2;
}
```

Method reference not suitable.

---

### 3. One-off Logic

If logic is **used once**, lambda is clearer than creating a new method.

---

## When Method Reference is Better

### 1. Reusing Existing Methods

```java
.map(String::toUpperCase)
```

Cleaner than:

```java
.map(s -> s.toUpperCase())
```

---

### 2. Improving Readability

```java
.forEach(System.out::println)
```

This reads almost like English.

---

### 3. Constructor / Factory Style

```java
.stream()
.map(User::new)
```

Very expressive.

---

## Readability Rule (GOLDEN RULE)

> **If method reference makes code more readable → use it**
> **If lambda explains logic better → use lambda**

Never use method references just to look “smart”.

---

## Performance: Lambda vs Method Reference

**No practical performance difference**

* Both compile to the same bytecode mechanism
* JVM uses `invokedynamic`
* Any difference is negligible

👉 Choose **readability**, not performance.

---

## Side-by-Side Comparison Table

| Aspect               | Lambda                | Method Reference |
| -------------------- | --------------------- | ---------------- |
| Purpose              | Write logic           | Reuse logic      |
| Readability          | Good for logic        | Best for reuse   |
| Custom behavior      | Yes                   | No               |
| Boilerplate          | Low                   | Lowest           |
| Performance          | Same                  | Same             |
| Interview preference | Both (explain choice) | Both             |

---

## Common Interview Trick Question

**Q:** Can every lambda be replaced with a method reference?
**A:** No.

Only lambdas that:

* Call **one existing method**
* Pass parameters **as-is**
* No extra logic

---

## Real-World Microservice Example

### Lambda

```java
orders.stream()
      .filter(o -> o.getAmount() > 1000)
      .map(o -> o.getId())
      .forEach(id -> process(id));
```

### Method Reference (Where Applicable)

```java
orders.stream()
      .filter(o -> o.getAmount() > 1000)
      .map(Order::getId)
      .forEach(this::process);
```

Clean, readable, professional.

---


