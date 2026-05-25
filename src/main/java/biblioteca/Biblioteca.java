package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<Libro> libros;

    public Biblioteca() {
        libros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarPorCodigo(String codigo) {
        for (Libro l : libros) {
            if (l.getCodigo().equalsIgnoreCase(codigo)) {
                return l;
            }
        }
        return null;
    }

    public List<Libro> listarLibros() {
        return new ArrayList<>(libros);
    }

    public List<Libro> buscarPorTituloParcial(String texto) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public boolean prestarLibro(String codigo) {
        Libro libro = buscarPorCodigo(codigo);
        if (libro == null || !libro.disponible()) {
            return false;
        }
        libro.prestar();
        return true;
    }

    public int totalLibrosDisponibles() {
        int total = 0;
        for (Libro l : libros) {
            total += l.getStock();
        }
        return total;
    }
}