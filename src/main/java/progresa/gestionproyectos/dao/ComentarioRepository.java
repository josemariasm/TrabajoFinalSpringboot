package progresa.gestionproyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.gestionproyectos.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}