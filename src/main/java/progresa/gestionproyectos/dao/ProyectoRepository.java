package progresa.gestionproyectos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import progresa.gestionproyectos.entity.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    boolean existsByNombre(String nombre);
}