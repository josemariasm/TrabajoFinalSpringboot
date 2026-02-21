package progresa.gestionproyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.gestionproyectos.entity.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
}