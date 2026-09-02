# Web Fundamentals

The layer beneath every Spring MVC/REST controller you'll write later in
this course. Understanding what Spring is automating away — a Servlet
container dispatching HTTP requests — makes the framework layers in later
sections click instead of feeling like magic.

## Topics

1. [Servlets & JSP (legacy context)](01-servlets-and-jsp.md)
2. [REST architecture principles](02-rest-architecture-principles.md)
3. [HTTP methods, status codes, headers](03-http-methods-status-codes-headers.md)

## Where this fits

```mermaid
flowchart TD
    Browser["Client / Browser / curl"] -->|HTTP request| Container["Servlet Container\n(Tomcat, embedded in Spring Boot)"]
    Container --> Servlet["HttpServlet.service()\ndoGet / doPost / doPut / doDelete"]
    Servlet -.pre-Spring: you write this by hand.-> Response["HttpServletResponse"]
    Servlet -.Spring Boot: DispatcherServlet wraps this.-> DS["DispatcherServlet"]
    DS --> Controller["@RestController method"]
    Controller --> Response
    Response -->|HTTP response| Browser
```

Every `@RestController` method you write in the Spring section still runs
*inside* a Servlet (Spring's `DispatcherServlet`) and still produces an
HTTP response governed by the same methods, status codes, and headers
covered here.
