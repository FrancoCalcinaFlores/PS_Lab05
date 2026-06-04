# Sistema de Biblioteca Inteligente (Web MVP)
Laboratorio 05 – Pruebas de Software (Caja Negra)

## Descripción General

Este proyecto consiste en un sistema de gestión de biblioteca  **MVP web** moderno, construido en **Spring Boot 3.3.2** (Java 21) y persistido mediante una base de datos local **SQLite**.

El sistema permite gestionar de manera visual e interactiva el catálogo de libros, los usuarios y el flujo de préstamos/devoluciones.

El proyecto incluye pruebas unitarias orientadas a Caja Negra (PE y AVL) para la lógica del servicio de negocio.

---

## Estructura del Proyecto

```text
PS_Lab05/
├── src/
│   ├── main/
│   │   ├── java/biblioteca/
│   │   │   ├── Application.java             # Clase de arranque Spring Boot
│   │   │   ├── config/                      # (Opcional) Configuraciones del sistema
│   │   │   ├── controller/                  # Controladores Spring MVC
│   │   │   ├── model/                       # Entidades JPA (Libro, Usuario, Prestamo)
│   │   │   ├── repository/                  # Repositorios JPA
│   │   │   └── service/                     # Lógica de Negocio (BibliotecaService)
│   │   └── resources/
│   │       ├── templates/                   # Vistas Thymeleaf (Bootstrap 5)
│   │       │   ├── layout.html              # Plantilla base y diseño común
│   │       │   ├── index.html               # Dashboard y Préstamos Activos
│   │       │   ├── libros.html              # Catálogo, registro y eliminación
│   │       │   ├── usuarios.html            # Registro y búsqueda de usuarios
│   │       │   └── prestamos.html           # Registro de préstamos
│   │       └── application.properties       # Configuración de SQLite y Hibernate
│   └── test/
│       └── java/biblioteca/
│           └── service/
│               └── BibliotecaServiceTest.java # Pruebas unitarias de PE y AVL (JUnit 5)
├── pom.xml
├── README.md
└── REQUIREMENTS.md
```

---

## Requisitos Previos

- Java JDK 17 o superior (Recomendado Java 21)
- Apache Maven
- Navegador Web moderno

Verificación de entorno:
```bash
java -version
mvn -version
```

---

## Compilación del Proyecto

Para compilar todo el proyecto y empaquetar los artefactos:
```bash
mvn clean compile
```

---

## Ejecución de la Aplicación

Para iniciar el servidor local embebido (Tomcat) en el puerto `8080`:
```bash
mvn spring-boot:run
```

Una vez ejecutado, abre tu navegador e ingresa a:
👉 [http://localhost:8080/](http://localhost:8080/)

---

## Ejecución de Pruebas Unitarias

Para correr las pruebas orientadas a Caja Negra (Particiones de Equivalencia y Análisis de Valores Límite):
```bash
mvn clean test
```

---

Autores:
- CHILO HUILLCA, OSCAR RAUL
- CALCINA FLORES, FRANCO
- VENERO GUEVARA, CHRISTIAN HENRY
- ALMANZA MAMANI, EDGAR RAUL

Curso: Pruebas de Software  
Laboratorio: 05  
Universidad Nacional de San Agustín (UNSA)
