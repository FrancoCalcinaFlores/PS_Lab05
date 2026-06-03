package biblioteca.repository;

import biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Libro> buscarPorTituloOAutor(@Param("query") String query);
}
