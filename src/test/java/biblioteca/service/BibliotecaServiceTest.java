package biblioteca.service;

import biblioteca.model.Libro;
import biblioteca.model.Usuario;
import biblioteca.model.Prestamo;
import biblioteca.repository.LibroRepository;
import biblioteca.repository.UsuarioRepository;
import biblioteca.repository.PrestamoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BibliotecaServiceTest {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @BeforeEach
    void setUp() {
        prestamoRepository.deleteAll();
        libroRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    // ==========================================
    // PARTICIÓN DE EQUIVALENCIA (PE)
    // ==========================================

    @Test
    void registrarLibro_Valido_PE() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 2);
        Libro resultado = bibliotecaService.registrarLibro(libro);

        assertNotNull(resultado);
        assertEquals("L01", resultado.getCodigo());
        assertTrue(libroRepository.existsById("L01"));
    }

    @Test
    void registrarLibro_CodigoDuplicado_PE() {
        Libro libro1 = new Libro("L01", "Clean Code", "Martin", "Software", 2);
        bibliotecaService.registrarLibro(libro1);

        Libro libro2 = new Libro("L01", "Clean Architecture", "Martin", "Software", 1);
        assertThrows(IllegalArgumentException.class, () -> bibliotecaService.registrarLibro(libro2));
    }

    @Test
    void registrarUsuario_Valido_PE() {
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");
        Usuario resultado = bibliotecaService.registrarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("U01", resultado.getCodigo());
        assertTrue(usuarioRepository.existsById("U01"));
    }

    @Test
    void registrarPrestamo_Valido_PE() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 2);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");
        
        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        Prestamo prestamo = bibliotecaService.registrarPrestamo("L01", "U01");

        assertNotNull(prestamo);
        assertTrue(prestamo.isActivo());
        
        Libro libroActualizado = bibliotecaService.buscarLibroPorCodigo("L01");
        assertEquals(1, libroActualizado.getStock()); // Decrementado de 2 a 1
    }

    @Test
    void registrarPrestamo_UsuarioInexistente_PE() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 2);
        bibliotecaService.registrarLibro(libro);

        assertThrows(IllegalArgumentException.class, () -> bibliotecaService.registrarPrestamo("L01", "U99"));
    }

    @Test
    void registrarDevolucion_Valida_PE() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 1);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");

        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        Prestamo prestamo = bibliotecaService.registrarPrestamo("L01", "U01");
        
        bibliotecaService.registrarDevolucion(prestamo.getId());

        assertFalse(prestamo.isActivo());
        
        Libro libroActualizado = bibliotecaService.buscarLibroPorCodigo("L01");
        assertEquals(1, libroActualizado.getStock()); // Era 1 -> prestado a 0 -> devuelto a 1
        assertFalse(prestamoRepository.findById(prestamo.getId()).get().isActivo());
    }

    @Test
    void registrarDevolucion_YaDevuelto_PE() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 1);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");

        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        Prestamo prestamo = bibliotecaService.registrarPrestamo("L01", "U01");
        bibliotecaService.registrarDevolucion(prestamo.getId());

        // Intentar devolver otra vez
        assertThrows(IllegalStateException.class, () -> bibliotecaService.registrarDevolucion(prestamo.getId()));
    }

    // ==========================================
    // ANÁLISIS DE VALORES LÍMITE (AVL)
    // ==========================================

    @Test
    void registrarLibro_StockMenosUno_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", -1);
        
        assertThrows(IllegalArgumentException.class, () -> bibliotecaService.registrarLibro(libro));
    }

    @Test
    void registrarLibro_StockCero_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 0);
        Libro resultado = bibliotecaService.registrarLibro(libro);

        assertNotNull(resultado);
        assertEquals(0, resultado.getStock());
    }

    @Test
    void registrarLibro_StockUno_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 1);
        Libro resultado = bibliotecaService.registrarLibro(libro);

        assertNotNull(resultado);
        assertEquals(1, resultado.getStock());
    }

    @Test
    void registrarPrestamo_StockCero_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 0);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");

        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        assertThrows(IllegalStateException.class, () -> bibliotecaService.registrarPrestamo("L01", "U01"));
    }

    @Test
    void registrarPrestamo_StockUno_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 1);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");

        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        Prestamo prestamo = bibliotecaService.registrarPrestamo("L01", "U01");

        assertNotNull(prestamo);
        Libro libroActualizado = bibliotecaService.buscarLibroPorCodigo("L01");
        assertEquals(0, libroActualizado.getStock());
    }

    @Test
    void registrarPrestamo_StockDos_AVL() {
        Libro libro = new Libro("L01", "Clean Code", "Martin", "Software", 2);
        Usuario usuario = new Usuario("U01", "Juan", "juan@email.com");

        bibliotecaService.registrarLibro(libro);
        bibliotecaService.registrarUsuario(usuario);

        Prestamo prestamo = bibliotecaService.registrarPrestamo("L01", "U01");

        assertNotNull(prestamo);
        Libro libroActualizado = bibliotecaService.buscarLibroPorCodigo("L01");
        assertEquals(1, libroActualizado.getStock());
    }

    @Test
    void registrarLibro_CodigoNulo_AVL() {
        Libro libro = new Libro(null, "Clean Code", "Martin", "Software", 2);

        assertThrows(IllegalArgumentException.class, () -> bibliotecaService.registrarLibro(libro));
    }

    @Test
    void registrarLibro_CodigoVacio_AVL() {
        Libro libro = new Libro("   ", "Clean Code", "Martin", "Software", 2);

        assertThrows(IllegalArgumentException.class, () -> bibliotecaService.registrarLibro(libro));
    }
}
