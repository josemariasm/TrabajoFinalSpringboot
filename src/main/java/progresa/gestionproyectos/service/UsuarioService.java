package progresa.gestionproyectos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progresa.gestionproyectos.dao.UsuarioRepository;
import progresa.gestionproyectos.entity.Usuario;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() { return usuarioRepository.findAll(); }
    public Optional<Usuario> findById(Long id) { return usuarioRepository.findById(id); }
    public boolean existsById(Long id) { return usuarioRepository.existsById(id); }
    public boolean existsByNombre(String nombre) { return usuarioRepository.existsByNombre(nombre); }
    public void save(Usuario usuario) { usuarioRepository.save(usuario); }
    public void deleteById(Long id) { usuarioRepository.deleteById(id); }
    public List<Usuario> findAllById(List<Long> ids) { return usuarioRepository.findAllById(ids); }
}