package progresa.gestionproyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.gestionproyectos.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
}