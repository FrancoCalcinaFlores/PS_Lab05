package biblioteca;

public class Libro {

    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int stock;

    public Libro(String codigo, String titulo, String autor, String categoria, int stock) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stock = stock;
    }

    public boolean disponible() {
        return stock > 0;
    }

    public void prestar() {
        if (stock <= 0) {
            throw new IllegalStateException();
        }
        stock--;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }
}