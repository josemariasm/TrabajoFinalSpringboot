package progresa.gestionproyectos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progresa.gestionproyectos.dao.*;
import progresa.gestionproyectos.entity.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TareaService {
    @Autowired private TareaRepository tareaRepository;
    @Autowired private EstadoRepository estadoRepository;
    @Autowired private ComentarioRepository comentarioRepository;

    public List<Tarea> getTareasPorProyecto(Long id) { return tareaRepository.findByProyectoId(id); }
    public Optional<Tarea> findById(Long id) { return tareaRepository.findById(id); }
    public boolean existsById(Long id) { return tareaRepository.existsById(id); }
    public void save(Tarea tarea) { tareaRepository.save(tarea); }
    public void deleteById(Long id) { tareaRepository.deleteById(id); }

    public Optional<Estado> findEstadoById(Long id) { return estadoRepository.findById(id); }
    public void saveComentario(Comentario c) { comentarioRepository.save(c); }
}