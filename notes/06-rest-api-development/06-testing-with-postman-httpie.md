# Testing with Postman/HTTPie

Before writing automated tests (or often, in parallel with early
development), you need a fast way to manually send requests to an API and
inspect the response — verifying an endpoint by hand, exploring an
unfamiliar API, or reproducing a bug reported by another team. Postman
(GUI) and HTTPie (CLI) are the two most common tools for this, and they
solve the same problem with different tradeoffs.

## Before → After: testing an endpoint via raw `curl` vs. a purpose-built tool

**Before — `curl`, technically capable of everything, but verbose and
error-prone for anything beyond a trivial `GET`:**

```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{"customerName": "Alice", "items": [{"itemId": "1", "quantity": 2}]}' \
  -w "\nStatus: %{http_code}\n"
```

Functional, but every flag (`-X`, `-H`, `-d`, `-w`) has to be remembered
and typed correctly, JSON has to be manually quoted and escaped in the
shell, and there's no persistent record of "the requests I use to test
this API" beyond your shell history.

**After — HTTPie, the same request with far less ceremony:**

```bash
http POST localhost:8080/api/v1/orders \
  Authorization:"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  customerName="Alice" \
  items:='[{"itemId": "1", "quantity": 2}]'
```

HTTPie defaults to JSON (no `-H "Content-Type: application/json"`
needed), colorizes and pretty-prints the response automatically, and
uses a simpler syntax: `key=value` for a JSON string field, `key:=value`
for raw JSON (numbers, arrays, objects, booleans).

```mermaid
flowchart LR
    subgraph Curl["curl"]
        C1["-X POST"] --> C2["-H per header"] --> C3["-d 'raw JSON string'"] --> C4["manual response parsing"]
    end
    subgraph HTTPie["HTTPie"]
        H1["http POST url"] --> H2["key=value shorthand"] --> H3["auto Content-Type"] --> H4["pretty-printed, colorized response"]
    end
```

## HTTPie — common patterns

```bash
# GET with query params — no manual URL encoding needed
http GET localhost:8080/api/v1/orders status==SHIPPED page==0 size==20

# POST with a JSON body
http POST localhost:8080/api/v1/orders customerName="Alice" total:=99.99

# PATCH with auth header
http PATCH localhost:8080/api/v1/orders/42 \
  Authorization:"Bearer $TOKEN" \
  status="CANCELLED"

# See the full request AND response (headers + body) — invaluable for debugging
http --print=HhBb GET localhost:8080/api/v1/orders/42

# File upload
http --form POST localhost:8080/api/v1/documents file@./report.pdf
```

`==` is query params, `=` is a string body field, `:=` is raw JSON (for
numbers, booleans, arrays, nested objects) — this distinction is the main
thing to learn, and it removes almost all manual JSON-string-escaping
compared to `curl -d`.

## Postman — the GUI alternative, and why teams use it instead

```mermaid
flowchart TD
    Postman["Postman"] --> Collections["Collections\ngrouped, saved, shareable sets of requests"]
    Postman --> Environments["Environments\nswap base URL/tokens per env\n(dev/staging/prod) with one dropdown"]
    Postman --> Scripts["Pre-request & test scripts\nJavaScript, run automatically per request"]
    Postman --> Runner["Collection Runner\nrun an entire collection as a suite,\nsee pass/fail per request"]
```

**Before — every teammate manually reconstructs requests from
documentation or old Slack messages, no shared source of truth:**

Each developer testing the same API writes their own ad hoc `curl`/HTTPie
commands, independently — no shared record of "here's exactly how to call
this endpoint," and no easy way to onboard a new teammate onto testing
this API quickly.

**After — a shared Postman collection, checked in or shared via Postman's
workspace, is the API's live, executable reference:**

```json
// A saved Postman request (simplified) — reusable, shareable, versioned
{
  "name": "Create Order",
  "request": {
    "method": "POST",
    "url": "{{baseUrl}}/api/v1/orders",
    "header": [{ "key": "Authorization", "value": "Bearer {{authToken}}" }],
    "body": {
      "mode": "raw",
      "raw": "{\"customerName\": \"Alice\", \"items\": [...]}"
    }
  }
}
```

`{{baseUrl}}` and `{{authToken}}` are **environment variables** — the
exact same collection runs against local, staging, or production simply
by switching Postman's active environment, without editing a single
request.

**Postman test scripts — turning manual exploration into repeatable
checks:**

```javascript
// Attached to the "Create Order" request — runs automatically after each send
pm.test("Status code is 201", () => {
    pm.response.to.have.status(201);
});

pm.test("Response has a Location header", () => {
    pm.response.to.have.header("Location");
});

pm.test("Order total matches request", () => {
    const body = pm.response.json();
    pm.expect(body.total).to.eql(199.98);
});
```

```mermaid
sequenceDiagram
    participant Dev
    participant Postman
    participant API

    Dev->>Postman: select environment (e.g. "staging"), click Send
    Postman->>API: request, using {{baseUrl}}/{{authToken}} resolved for staging
    API-->>Postman: response
    Postman->>Postman: run attached test script against the response
    Postman-->>Dev: pass/fail shown inline, plus pretty-printed response body
```

## Postman vs. HTTPie — when each one fits

| | Postman | HTTPie |
|---|---|---|
| Interface | GUI application | Command line |
| Best for | Exploring a complex API, saving/sharing a full collection, team collaboration, non-technical stakeholders testing manually | Quick one-off requests, scripting, CI pipelines, developers who live in a terminal |
| Persistence | Collections + environments, shareable via workspace/export | Shell history, or shell scripts/aliases you write yourself |
| Scripting/assertions | Built-in (pre-request + test scripts, JavaScript) | Requires piping to another tool (`jq`, shell scripting) for assertions |
| Learning curve | Higher — a full application to learn | Lower — closer to `curl`, familiar to anyone comfortable in a shell |
| Automation-friendly | Newman (Postman's CLI runner) can run collections in CI | Naturally scriptable, drops straight into shell scripts/CI steps |

## Real advantages

- **Manual testing catches problems automated tests haven't been written
  for yet** — especially early in development, or when exploring an
  unfamiliar API, being able to poke at a live endpoint and actually see
  the response is faster than writing a test first.
- **Postman collections double as living, executable documentation** —
  arguably more trustworthy than the OpenAPI-generated docs from the
  previous file for showing *realistic* usage sequences (create an order,
  then fetch it, then cancel it), since a collection can chain requests
  together the way a real client would.
- **HTTPie's terseness makes it genuinely fast for iterative debugging**
  — tweaking one field and re-running a request is a quick up-arrow-and-
  edit in a terminal, versus navigating a GUI form for the same change.

## Caveats

- **Manual testing is not a substitute for automated tests.** Both tools
  are excellent for exploration and one-off verification, but neither
  replaces a proper test suite (unit tests, `@SpringBootTest` integration
  tests) that runs automatically on every change and catches regressions
  without a human remembering to click "Send" again.
- **Postman collections can drift from the real API** just like the
  hand-written documentation problem from the OpenAPI file — a collection
  isn't automatically regenerated from code, so it needs the same
  discipline to keep updated as any other artifact maintained by hand.
- **Secrets in a shared Postman environment (API tokens, passwords) are a
  real leak risk** if the workspace or exported collection is shared
  carelessly — Postman supports marking variables as "secret" specifically
  for this reason; using plain environment variables for credentials in a
  collection that gets shared or committed is a common, avoidable mistake.
