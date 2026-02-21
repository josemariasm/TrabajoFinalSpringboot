package progresa.gestionproyectos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progresa.gestionproyectos.dao.ProyectoRepository;
import progresa.gestionproyectos.entity.Proyecto;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> getAll() { return proyectoRepository.findAll(); }
    public Optional<Proyecto> findById(Long id) { return proyectoRepository.findById(id); }
    public boolean existsById(Long id) { return proyectoRepository.existsById(id); }
    public boolean existsByNombre(String nombre) { return proyectoRepository.existsByNombre(nombre); }
    public void save(Proyecto proyecto) { proyectoRepository.save(proyecto); }
    public void deleteById(Long id) { proyectoRepository.deleteById(id); }
}