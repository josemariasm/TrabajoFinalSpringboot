package progresa.gestionproyectos.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.gestionproyectos.dto.Mensaje;
import progresa.gestionproyectos.entity.Usuario;
import progresa.gestionproyectos.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        if (StringUtils.isBlank(usuario.getNombre())) {
            return new ResponseEntity<>(new Mensaje("El nombre del usuario es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByNombre(usuario.getNombre())) {
            return new ResponseEntity<>(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }

        usuarioService.save(usuario);
        return new ResponseEntity<>(new Mensaje("Usuario creado correctamente"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        if (!usuarioService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("No existe el usuario con el ID: " + id), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(usuarioActualizado.getNombre())) {
            return new ResponseEntity<>(new Mensaje("El nombre no puede estar vacío"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.findById(id).get();
        usuario.setNombre(usuarioActualizado.getNombre());
        usuarioService.save(usuario);

        return new ResponseEntity<>(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id) {
        if (!usuarioService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("No existe el usuario con el ID: " + id), HttpStatus.BAD_REQUEST);
        }
        usuarioService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Usuario eliminado correctamente"), HttpStatus.OK);
    }
}