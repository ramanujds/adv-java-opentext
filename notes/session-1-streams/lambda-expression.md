# Java Lambda Expressions 
## What is a Lambda Expression?

A **lambda expression** is a **short, anonymous function** that you can pass around as data.

Before Java 8:

* You passed **objects**
  After Java 8:
* You can pass **behavior**

Lambda expressions work **only with functional interfaces** (interfaces with exactly one abstract method).

---

## Basic Syntax

```java
(parameters) -> expression
```

or

```java
(parameters) -> {
    statements;
}
```

### Examples

```java
() -> System.out.println("Hello")
x -> x * x
(a, b) -> a + b
```

---

## Why Lambdas Were Introduced

1. Reduce **boilerplate code**
2. Make code **more readable**
3. Enable **functional programming**
4. Power **Streams API**

---

## Lambda vs Anonymous Class (MOST IMPORTANT)

### Before Java 8

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};
```

### With Lambda

```java
Runnable r = () -> System.out.println("Running");
```

Same logic, **90% less code**.

---

## Example 1: Runnable (No parameters, no return)

```java
Runnable task = () -> {
    System.out.println("Task started");
};
new Thread(task).start();
```

---

## Example 2: Predicate (Condition check)

```java
Predicate<Integer> isEven = n -> n % 2 == 0;

System.out.println(isEven.test(10)); // true
```

**Meaning:**

> “Take an integer and tell me if it is even”

---

## Example 3: Function (Transformation)

```java
Function<String, Integer> lengthFn = s -> s.length();

System.out.println(lengthFn.apply("Java")); // 4
```

---

## Example 4: Consumer (Action, no return)

```java
Consumer<String> printer = msg -> System.out.println(msg);

printer.accept("Hello Lambda");
```

---

## Example 5: Supplier (No input, gives output)

```java
Supplier<Double> randomValue = () -> Math.random();

System.out.println(randomValue.get());
```

---

## Example 6: Multiple Parameters

```java
BiFunction<Integer, Integer, Integer> sum =
        (a, b) -> a + b;

System.out.println(sum.apply(10, 20)); // 30
```

---

## Example 7: Lambda with Block Body

```java
Function<Integer, Integer> square = x -> {
    int result = x * x;
    return result;
};
```

Use block when:

* Multiple statements
* Loops
* Conditions

---

## Example 8: Lambda in Collections

### Sorting (Very common)

```java
List<String> names = List.of("Ram", "Shyam", "Amit");

names.stream()
     .sorted((a, b) -> a.compareTo(b))
     .forEach(System.out::println);
```

Or better:

```java
names.stream()
     .sorted(String::compareTo)
     .forEach(System.out::println);
```

---

## Example 9: Lambda in Streams (Real Power)

```java
List<Integer> numbers = List.of(1,2,3,4,5,6);

numbers.stream()
       .filter(n -> n % 2 == 0)
       .map(n -> n * n)
       .forEach(System.out::println);
```

Flow:

* `filter` → Predicate
* `map` → Function
* `forEach` → Consumer

---

## How Compiler Knows the Types (Type Inference)

```java
Predicate<Integer> p = x -> x > 10;
```

* Compiler knows `x` is `Integer`
* No need to write `(Integer x)`

You *can* write:

```java
(Integer x) -> x > 10
```

But nobody does 😄

---

## Lambda Rules 

1. Works only with **functional interfaces**
2. Can access **effectively final variables**
3. Cannot change local variables
4. `this` refers to **current class**, not lambda
5. No method name (anonymous)

---

## Example: Effectively Final Variable

```java
int limit = 10;

Predicate<Integer> p = n -> n > limit; // OK
```

Not allowed:

```java
limit = 20; // compile error
```

---

## Lambda vs Method Reference

Lambda:

```java
x -> System.out.println(x)
```

Method reference:

```java
System.out::println
```

Cleaner when logic already exists.

---

## When NOT to Use Lambda

* Complex business logic
* Long loops
* Too many conditions

Readability > cleverness.

---

## Real-World Use Case (Microservice Style)

```java
List<Order> orders = getOrders();

orders.stream()
      .filter(o -> o.getAmount() > 1000)
      .map(Order::getId)
      .forEach(this::processOrder);
```

Readable, declarative, expressive.

---

## One-Line Summary

> **Lambda expressions allow us to treat behavior as data and write concise, readable, functional-style code in Java.**


