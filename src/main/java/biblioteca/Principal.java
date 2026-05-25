package biblioteca;

import java.util.List;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.agregarLibro(new Libro("L01", "Clean Code", "Robert Martin", "Software", 2));
        biblioteca.agregarLibro(new Libro("L02", "Clean Architecture", "Robert Martin", "Software", 1));
        biblioteca.agregarLibro(new Libro("L03", "Refactoring", "Martin Fowler", "Software", 3));
        biblioteca.agregarLibro(new Libro("L04", "Domain Driven Design", "Eric Evans", "Software", 1));
        biblioteca.agregarLibro(new Libro("L05", "Design Patterns", "Gamma", "Software", 2));
        biblioteca.agregarLibro(new Libro("L06", "Ingenieria de Software", "Pressman", "Ingenieria", 4));
        biblioteca.agregarLibro(new Libro("L07", "Arquitectura de Software", "Bass", "Ingenieria", 0));
        biblioteca.agregarLibro(new Libro("L08", "Estructuras de Datos", "Weiss", "Programacion", 3));
        biblioteca.agregarLibro(new Libro("L09", "Algoritmos", "Cormen", "Programacion", 2));
        biblioteca.agregarLibro(new Libro("L10", "Bases de Datos", "Silberschatz", "Datos", 1));
        biblioteca.agregarLibro(new Libro("L11", "Sistemas Operativos", "Tanenbaum", "Ingenieria", 2));
        biblioteca.agregarLibro(new Libro("L12", "Redes de Computadoras", "Kurose", "Ingenieria", 2));
        biblioteca.agregarLibro(new Libro("L13", "Compiladores", "Aho", "Ingenieria", 0));
        biblioteca.agregarLibro(new Libro("L14", "Inteligencia Artificial", "Russell", "Ciencia", 3));
        biblioteca.agregarLibro(new Libro("L15", "Machine Learning", "Mitchell", "Ciencia", 2));
        biblioteca.agregarLibro(new Libro("L16", "Python para Todos", "Downey", "Programacion", 4));
        biblioteca.agregarLibro(new Libro("L17", "Java Avanzado", "Oracle", "Programacion", 1));
        biblioteca.agregarLibro(new Libro("L18", "Matematica Discreta", "Rosen", "Ciencia", 2));
        biblioteca.agregarLibro(new Libro("L19", "Calculo I", "Stewart", "Ciencia", 3));
        biblioteca.agregarLibro(new Libro("L20", "Fisica Universitaria", "Young", "Ciencia", 1));

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n1. Ver catalogo");
            System.out.println("2. Buscar libro por titulo");
            System.out.println("3. Prestar libro");
            System.out.println("4. Total libros disponibles");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    for (Libro l : biblioteca.listarLibros()) {
                        System.out.println(
                                l.getCodigo() + " | " +
                                l.getTitulo() + " | " +
                                l.getAutor() + " | " +
                                l.getCategoria() + " | Stock: " +
                                l.getStock() + " | " +
                                (l.disponible() ? "Disponible" : "Agotado")
                        );
                    }
                    break;

                case 2:
                    System.out.print("Ingrese texto a buscar: ");
                    String texto = sc.nextLine();
                    List<Libro> encontrados = biblioteca.buscarPorTituloParcial(texto);
                    for (Libro l : encontrados) {
                        System.out.println(l.getCodigo() + " | " + l.getTitulo());
                    }
                    break;

                case 3:
                    System.out.print("Ingrese codigo del libro: ");
                    String codigo = sc.nextLine();
                    if (biblioteca.prestarLibro(codigo)) {
                        System.out.println("Prestamo exitoso");
                    } else {
                        System.out.println("No se pudo realizar el prestamo");
                    }
                    break;

                case 4:
                    System.out.println("Total libros disponibles: " + biblioteca.totalLibrosDisponibles());
                    break;
            }

        } while (opcion != 0);

        sc.close();
    }
}