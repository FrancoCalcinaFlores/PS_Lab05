package biblioteca.repository;

import biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.codigo) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Usuario> buscarPorNombreOCodigo(@Param("query") String query);
}
