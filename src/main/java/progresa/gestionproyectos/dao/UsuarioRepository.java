package progresa.gestionproyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.gestionproyectos.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNombre(String nombre);
}