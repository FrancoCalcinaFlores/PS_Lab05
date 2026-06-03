package biblioteca.service;

import biblioteca.model.Libro;
import biblioteca.model.Usuario;
import biblioteca.model.Prestamo;
import biblioteca.repository.LibroRepository;
import biblioteca.repository.UsuarioRepository;
import biblioteca.repository.PrestamoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BibliotecaService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    @Autowired
    public BibliotecaService(LibroRepository libroRepository,
                             UsuarioRepository usuarioRepository,
                             PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    // RF01: Registrar Libro
    public Libro registrarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }
        if (libro.getCodigo() == null || libro.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del libro es requerido");
        }
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro es requerido");
        }
        if (libro.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (libroRepository.existsById(libro.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un libro con el código: " + libro.getCodigo());
        }
        return libroRepository.save(libro);
    }

    // RF02: Buscar Libro
    @Transactional(readOnly = true)
    public List<Libro> buscarLibros(String query) {
        if (query == null || query.trim().isEmpty()) {
            return libroRepository.findAll();
        }
        return libroRepository.buscarPorTituloOAutor(query);
    }

    // RF03: Eliminar Libro
    public void eliminarLibro(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del libro es requerido");
        }
        if (!libroRepository.existsById(codigo)) {
            throw new IllegalArgumentException("El libro con código " + codigo + " no existe");
        }
        if (prestamoRepository.existsByLibroCodigoAndActivoTrue(codigo)) {
            throw new IllegalStateException("No se puede eliminar el libro porque tiene préstamos activos");
        }
        libroRepository.deleteById(codigo);
    }

    // RF04: Registrar Usuario
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (usuario.getCodigo() == null || usuario.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del usuario es requerido");
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es requerido");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del usuario es requerido");
        }
        if (usuarioRepository.existsById(usuario.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un usuario con el código: " + usuario.getCodigo());
        }
        return usuarioRepository.save(usuario);
    }

    // RF05: Buscar Usuario
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuarios(String query) {
        if (query == null || query.trim().isEmpty()) {
            return usuarioRepository.findAll();
        }
        return usuarioRepository.buscarPorNombreOCodigo(query);
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorCodigo(String codigo) {
        return usuarioRepository.findById(codigo).orElse(null);
    }

    @Transactional(readOnly = true)
    public Libro buscarLibroPorCodigo(String codigo) {
        return libroRepository.findById(codigo).orElse(null);
    }

    // RF06: Registrar Préstamo
    public Prestamo registrarPrestamo(String libroCodigo, String usuarioCodigo) {
        if (libroCodigo == null || libroCodigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del libro es requerido");
        }
        if (usuarioCodigo == null || usuarioCodigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del usuario es requerido");
        }

        Libro libro = libroRepository.findById(libroCodigo)
                .orElseThrow(() -> new IllegalArgumentException("El libro con código " + libroCodigo + " no existe"));

        Usuario usuario = usuarioRepository.findById(usuarioCodigo)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con código " + usuarioCodigo + " no existe"));

        if (!libro.disponible()) {
            throw new IllegalStateException("El libro con código " + libroCodigo + " no tiene stock disponible para préstamo");
        }

        libro.prestar();
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo(libro, usuario, LocalDate.now());
        return prestamoRepository.save(prestamo);
    }

    // RF07: Registrar Devolución
    public void registrarDevolucion(Long prestamoId) {
        if (prestamoId == null) {
            throw new IllegalArgumentException("El ID del préstamo es requerido");
        }

        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("El préstamo con ID " + prestamoId + " no existe"));

        if (!prestamo.isActivo()) {
            throw new IllegalStateException("El préstamo ya ha sido devuelto");
        }

        Libro libro = prestamo.getLibro();
        libro.devolver();
        libroRepository.save(libro);

        prestamo.setActivo(false);
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoRepository.save(prestamo);
    }

    // RF08: Listar Préstamos Activos
    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosActivos() {
        return prestamoRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public int totalLibrosDisponibles() {
        return libroRepository.findAll().stream().mapToInt(Libro::getStock).sum();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
}
