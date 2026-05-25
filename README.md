# Sistema de Biblioteca Inteligente
Laboratorio 05 – Pruebas de Software (Caja Negra)

## Descripción General

Este proyecto implementa un Sistema de Biblioteca Inteligente desarrollado en Java utilizando Maven como herramienta de construcción.
El sistema permite gestionar un catálogo de libros, consultar disponibilidad, realizar préstamos y ejecutar búsquedas mediante una interfaz de consola.

El proyecto incluye pruebas unitarias automatizadas orientadas a Caja Negra, aplicando las técnicas de:
- Partición de Equivalencia (PE)
- Análisis de Valores Límite (AVL)

La validación y verificación del sistema será realizada por un equipo externo.

---

## Estructura del Proyecto

PS_Lab05/
├── src/
│   ├── main/java/biblioteca/
│   └── test/java/biblioteca/
├── pom.xml
├── README.md
└── REQUIREMENTS.md

---

## Requisitos Previos

- Java JDK 8 o superior
- Apache Maven
- Sistema operativo compatible con Java

Verificación:

java -version  
mvn -version

---

## Compilación del Proyecto

mvn compile

---

## Ejecución de la Aplicación

mvn exec:java

La aplicación se ejecuta por consola y permite:
- Visualizar el catálogo de libros
- Buscar libros por título
- Prestar libros según disponibilidad
- Consultar el total de libros disponibles

---

## Ejecución de Pruebas Unitarias

mvn clean test

Todas las pruebas deben ejecutarse sin errores.

---

Autor: Franco Calcina Flores  
Curso: Pruebas de Software  
Laboratorio: 05
