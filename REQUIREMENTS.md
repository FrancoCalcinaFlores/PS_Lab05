# Requisitos del Sistema - Biblioteca Inteligente Web

## Requisitos Funcionales (RF)

- **RF01 Registrar Libro**: Permite almacenar un libro nuevo especificando código (único y obligatorio), título, autor, categoría y stock (entero no negativo).
- **RF02 Buscar Libro**: Permite buscar libros a través de coincidencias de texto parcial en el título o autor.
- **RF03 Eliminar Libro**: Permite remover un libro del catálogo por su código. Solo se permite la eliminación si el libro no posee préstamos activos.
- **RF04 Registrar Usuario**: Permite registrar a un lector especificando un código (único y obligatorio), nombre completo y correo electrónico.
- **RF05 Buscar Usuario**: Permite buscar usuarios mediante texto parcial que coincida con su nombre o código.
- **RF06 Registrar Préstamo**: Permite prestar un libro a un usuario. Verifica la existencia de ambos y que el libro cuente con stock disponible. Al confirmarse, decrementa el stock en 1 y crea un registro de préstamo activo.
- **RF07 Registrar Devolución**: Permite marcar un préstamo activo como devuelto. Al realizarse, incrementa el stock del libro respectivo en 1 y guarda la fecha de devolución.
- **RF08 Listar Préstamos Activos**: Muestra en pantalla todos los préstamos que aún no han sido devueltos.

---

## Requisitos No Funcionales (RNF)

- **RNF01 Autonomía de infraestructura**: No requiere servicios externos ni servidores de base de datos. SQLite es la única dependencia de almacenamiento, embebida directamente en la aplicación.
- **RNF02 Usabilidad web responsiva**: La interfaz se adapta a distintos tamaños de pantalla mediante Bootstrap 5.3.3, con sidebar fija, scroll independiente y layout flexible.
- **RNF03 Mantenibilidad y experiencia de usuario consistente**: Código organizado en capas controller/service/repository.

---

## Especificación de Pruebas Unitarias (Caja Negra)

### 1. Partición de Equivalencia (PE)

| Caso de Prueba | Entrada | Salida Esperada | Tipo |
| :--- | :--- | :--- | :--- |
| Registrar libro con código único | Libro con datos completos y código L100 | Registro exitoso, retorna el libro | Válido |
| Registrar libro con código duplicado | Código L01 (ya existente) | Lanza `IllegalArgumentException` | Inválido |
| Registrar préstamo con stock disponible | Libro con Stock = 2 y Usuario existente | Préstamo exitoso, Stock final = 1 | Válido |
| Registrar préstamo de usuario inexistente| Libro válido y Usuario "U99" | Lanza `IllegalArgumentException` | Inválido |
| Registrar devolución de préstamo activo| Préstamo ID 10 (activo), Libro Stock = 1 | Devuelto con éxito, Stock final = 2 | Válido |
| Registrar devolución de préstamo ya devuelto| Préstamo ID 10 (inactivo) | Lanza `IllegalStateException` | Inválido |

### 2. Análisis de Valores Límite (AVL)

| Variable/Parámetro | Valor Límite Evaluado | Salida Esperada | Comportamiento |
| :--- | :--- | :--- | :--- |
| Stock de Libro (Registro) | Stock = -1 | Lanza `IllegalArgumentException` | Límite inferior inválido |
| Stock de Libro (Registro) | Stock = 0 | Registro exitoso, libro no prestable | Límite inferior válido |
| Stock de Libro (Registro) | Stock = 1 | Registro exitoso, libro prestable | Límite inferior válido |
| Stock de Libro (Préstamo) | Stock = 0 | Lanza `IllegalStateException` | Límite crítico (No prestable) |
| Stock de Libro (Préstamo) | Stock = 1 | Préstamo exitoso, Stock final = 0 | Límite mínimo para préstamo |
| Stock de Libro (Préstamo) | Stock = 2 | Préstamo exitoso, Stock final = 1 | Límite normal para préstamo |
| Código de Libro (Vacío) | Código = "   " | Lanza `IllegalArgumentException` | Límite inferior de longitud |
| Código de Libro (Nulo) | Código = `null` | Lanza `IllegalArgumentException` | Valor límite nulo |
