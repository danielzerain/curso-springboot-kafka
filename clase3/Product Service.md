---
title: Product Service - Clase 2
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# Product Service - Clase 2

Base URLs:

* <a href="http://localhost:8080">Entorno desarrollo: http://localhost:8080</a>

# Authentication

# Raíz/Producto

## POST Crear producto

POST /api/products

> Body Parameters

```json
{
  "name": "Laptop",
  "description": "Modelo 2025",
  "price": 1299.9,
  "stock": 10
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Content-Type|header|string| yes |none|
|body|body|object| no |none|
|» name|body|string| yes |none|
|» description|body|string| yes |none|
|» price|body|number| yes |none|
|» stock|body|integer| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## GET Listar productos

GET /api/products

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## GET Listar productos Copy

GET /api/products/summary

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## GET Obtener producto por id

GET /api/products/1

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## PUT Actualizar producto

PUT /api/products/1

> Body Parameters

```json
{
  "name": "Laptop",
  "description": "Modelo 2025 -- actualizado",
  "price": 2000.9,
  "stock": 5
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Content-Type|header|string| yes |none|
|body|body|object| no |none|
|» name|body|string| yes |none|
|» description|body|string| yes |none|
|» price|body|number| yes |none|
|» stock|body|integer| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## DELETE Eliminar producto

DELETE /api/products/1

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

# Raíz/Categoria

## POST Crear categoria

POST /api/categories

> Body Parameters

```json
{
  "name": "Laptop",
  "description": "Modelo 2025",
  "price": 1299.9,
  "stock": 10
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Content-Type|header|string| yes |none|
|body|body|object| no |none|
|» name|body|string| yes |none|
|» description|body|string| yes |none|
|» price|body|number| yes |none|
|» stock|body|integer| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## GET Obtener Productos/Categoria

GET /api/categories/2/products

> Body Parameters

```json
{
  "name": "Laptop",
  "description": "Modelo 2025",
  "price": 1299.9,
  "stock": 10
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Content-Type|header|string| yes |none|
|body|body|object| no |none|
|» name|body|string| yes |none|
|» description|body|string| yes |none|
|» price|body|number| yes |none|
|» stock|body|integer| yes |none|

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

# Data Schema

