# 🛒 Microservicio: Inventario y Productos

Este microservicio gestiona el catálogo de productos disponibles en PetLovers, así como el inventario y disponibilidad de los mismos.

Incluye:
- Gestión de productos (alimentos, juguetes, medicinas, etc.)
- Control de inventario
- Detección de alertas por bajo stock

## 📦 Tecnologías usadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

## 📁 Estructura del microservicio

- `Producto`: nombre, descripción, precio y categoría.
- `Inventario`: cantidad disponible y umbral de alerta asociado a cada producto.

## 📚 Endpoints disponibles

### 📦 Productos

| Método | Ruta                 | Descripción                     |
|--------|----------------------|---------------------------------|
| POST   | `/api/productos`     | Registrar un nuevo producto     |
| GET    | `/api/productos`     | Listar todos los productos      |
| GET    | `/api/productos/{id}`| Obtener producto por ID         |
| DELETE | `/api/productos/{id}`| Eliminar producto por ID        |

### 🧮 Inventario

| Método | Ruta                                  | Descripción                                            |
|--------|---------------------------------------|--------------------------------------------------------|
| POST   | `/api/stock`                          | Registrar un nuevo registro de inventario             |
| GET    | `/api/stock`                          | Listar todos los inventarios                          |
| GET    | `/api/stock/{id}`                     | Obtener inventario por ID (registro)                  |
| GET    | `/api/stock/producto/{idProducto}`    | Obtener inventario por ID de producto                 |
| GET    | `/api/stock/alerta/{idProducto}`      | Verifica si un producto está en estado de alerta      |
| DELETE | `/api/stock/{id}`                     | Eliminar inventario por ID                            |

> 🔔 **`/api/stock/alerta/{idProducto}`**  
> Retorna `true` si el inventario del producto tiene `cantidad <= umbralAlerta`.  
> Ejemplo de respuesta: `true` o `false`.

---

## ⚙️ Configuración base (`application.yml`)

```yaml
server:
  port: 8083

spring:
  application:
    name: inventario-productos
  datasource:
    url: jdbc:postgresql://localhost:5432/petlovers_inventario
    username: tu_usuario
    password: tu_contraseña
  jpa:
    hibernate.ddl-auto: none
    show-sql: true
