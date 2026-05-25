# Requisitos del Sistema
Sistema de Biblioteca Inteligente

## Requisitos Funcionales

1. Mostrar un catálogo de libros con código, título, autor, categoría y stock.
2. Permitir la búsqueda de libros por texto parcial.
3. Permitir el préstamo de libros únicamente si existe stock disponible.
4. Mostrar el total de libros disponibles.
5. Todas las interacciones se realizan mediante consola.
6. El sistema debe incluir pruebas unitarias orientadas a Caja Negra.

## Requisitos No Funcionales

1. El sistema debe ejecutarse en Java 8 o superior.
2. El sistema debe construirse mediante Maven.
3. El sistema no debe requerir base de datos ni servicios externos.
4. El código debe ser claro y mantenible.

## Requisitos de Pruebas

1. Aplicar Partición de Equivalencia (PE).
2. Aplicar Análisis de Valores Límite (AVL).
3. Todas las pruebas deben ejecutarse automáticamente.
4. Las pruebas deben pasar sin errores.

## Dependencias

- JDK 8 o superior
- Apache Maven
- JUnit 5

## Observaciones

La verificación y validación del sistema será realizada por un equipo distinto al desarrollador.
