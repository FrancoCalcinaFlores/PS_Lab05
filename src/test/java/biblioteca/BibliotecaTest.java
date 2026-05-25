package biblioteca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

    @Test
    void prestarLibro_conStock_valido() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L01", "Clean Code", "Martin", "Software", 2));

        assertTrue(b.prestarLibro("L01"));
    }

    @Test
    void prestarLibro_sinStock_invalido() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L02", "Java", "Oracle", "Prog", 0));

        assertFalse(b.prestarLibro("L02"));
    }

    @Test
    void prestarLibro_codigoInexistente() {
        Biblioteca b = new Biblioteca();

        assertFalse(b.prestarLibro("X99"));
    }

    @Test
    void prestarLibro_stockUno_valorLimiteInferior() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L03", "DDD", "Evans", "Software", 1));

        assertTrue(b.prestarLibro("L03"));
        assertFalse(b.prestarLibro("L03"));
    }

    @Test
    void prestarLibro_stockDos_limiteSuperiorSimple() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L04", "Refactoring", "Fowler", "Software", 2));

        assertTrue(b.prestarLibro("L04"));
        assertTrue(b.prestarLibro("L04"));
        assertFalse(b.prestarLibro("L04"));
    }

    @Test
    void buscarPorTituloParcial_coincidencia() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L05", "Ingenieria de Software", "Pressman", "Ing", 1));

        assertEquals(1, b.buscarPorTituloParcial("software").size());
    }

    @Test
    void buscarPorTituloParcial_sinCoincidencias() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L06", "Matematica Discreta", "Rosen", "Ciencia", 1));

        assertEquals(0, b.buscarPorTituloParcial("java").size());
    }

    @Test
    void listarLibros_bibliotecaVacia() {
        Biblioteca b = new Biblioteca();

        assertEquals(0, b.listarLibros().size());
    }

    @Test
    void listarLibros_conElementos() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L07", "Sistemas Operativos", "Tanenbaum", "Ing", 3));
        b.agregarLibro(new Libro("L08", "Redes", "Kurose", "Ing", 2));

        assertEquals(2, b.listarLibros().size());
    }

    @Test
    void totalLibrosDisponibles_sumaCorrecta() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L09", "BD", "Silberschatz", "Ing", 2));
        b.agregarLibro(new Libro("L10", "IA", "Russell", "Ciencia", 3));

        assertEquals(5, b.totalLibrosDisponibles());
    }

    @Test
    void totalLibrosDisponibles_cero() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L11", "Compiladores", "Aho", "Ing", 0));

        assertEquals(0, b.totalLibrosDisponibles());
    }

    @Test
    void buscarPorCodigo_existente() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L12", "Algoritmos", "Cormen", "Ing", 1));

        assertNotNull(b.buscarPorCodigo("L12"));
    }

    @Test
    void buscarPorCodigo_noExistente() {
        Biblioteca b = new Biblioteca();

        assertNull(b.buscarPorCodigo("ZZZ"));
    }

    @Test
    void prestarLibro_multiplesLibros_independientes() {
        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("L13", "Libro A", "Autor", "Cat", 1));
        b.agregarLibro(new Libro("L14", "Libro B", "Autor", "Cat", 1));

        assertTrue(b.prestarLibro("L13"));
        assertTrue(b.prestarLibro("L14"));
        assertFalse(b.prestarLibro("L13"));
    }
}