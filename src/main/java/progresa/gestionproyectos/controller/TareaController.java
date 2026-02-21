package progresa.gestionproyectos.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.gestionproyectos.dto.Mensaje;
import progresa.gestionproyectos.entity.*;
import progresa.gestionproyectos.service.ProyectoService;
import progresa.gestionproyectos.service.TareaService;
import progresa.gestionproyectos.service.UsuarioService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired private TareaService tareaService;
    @Autowired private ProyectoService proyectoService;
    @Autowired private UsuarioService usuarioService;

    @PostMapping("/crear/{idProyecto}")
    public ResponseEntity<?> crearTarea(@PathVariable Long idProyecto, @RequestBody Tarea tarea) {
        if (StringUtils.isBlank(tarea.getTitulo())) {
            return new ResponseEntity<>(new Mensaje("El título de la tarea es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (!proyectoService.existsById(idProyecto)) {
            return new ResponseEntity<>(new Mensaje("El proyecto indicado no existe"), HttpStatus.BAD_REQUEST);
        }

        Proyecto proyecto = proyectoService.findById(idProyecto).get();
        tarea.setProyecto(proyecto);
        tareaService.save(tarea);

        return new ResponseEntity<>(new Mensaje("Tarea creada y asociada al proyecto correctamente"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        if (!tareaService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("La tarea no existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(tareaActualizada.getTitulo())) {
            return new ResponseEntity<>(new Mensaje("El título no puede estar vacío"), HttpStatus.BAD_REQUEST);
        }

        Tarea tarea = tareaService.findById(id).get();
        tarea.setTitulo(tareaActualizada.getTitulo());
        tarea.setDescripcion(tareaActualizada.getDescripcion());
        tarea.setFechaVencimiento(tareaActualizada.getFechaVencimiento());

        tareaService.save(tarea);
        return new ResponseEntity<>(new Mensaje("Tarea actualizada correctamente"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarTarea(@PathVariable Long id) {
        if (!tareaService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("No existe la tarea"), HttpStatus.BAD_REQUEST);
        }
        tareaService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Tarea eliminada correctamente"), HttpStatus.OK);
    }

    @PutMapping("/{idTarea}/asignarUsuario/{idUsuario}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long idTarea, @PathVariable Long idUsuario) {
        if (!tareaService.existsById(idTarea) || !usuarioService.existsById(idUsuario)) {
            return new ResponseEntity<>(new Mensaje("La tarea o el usuario no existen"), HttpStatus.BAD_REQUEST);
        }

        Tarea tarea = tareaService.findById(idTarea).get();
        Usuario usuario = usuarioService.findById(idUsuario).get();

        tarea.setUsuarioAsignado(usuario);
        tareaService.save(tarea);
        return new ResponseEntity<>(new Mensaje("Usuario asignado a la tarea con éxito"), HttpStatus.OK);
    }

    @PutMapping("/{idTarea}/cambiarEstado/{idEstado}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idTarea, @PathVariable Long idEstado) {
        if (!tareaService.existsById(idTarea)) {
            return new ResponseEntity<>(new Mensaje("La tarea no existe"), HttpStatus.BAD_REQUEST);
        }

        var estadoOpt = tareaService.findEstadoById(idEstado);
        if (estadoOpt.isEmpty()) {
            return new ResponseEntity<>(new Mensaje("El estado indicado no existe"), HttpStatus.BAD_REQUEST);
        }

        Tarea tarea = tareaService.findById(idTarea).get();
        tarea.setEstado(estadoOpt.get());
        tareaService.save(tarea);

        return new ResponseEntity<>(new Mensaje("Estado de la tarea actualizado"), HttpStatus.OK);
    }

    @PostMapping("/{idTarea}/comentar/{idUsuario}")
    public ResponseEntity<?> agregarComentario(@PathVariable Long idTarea, @PathVariable Long idUsuario, @RequestBody Comentario comentario) {
        if (StringUtils.isBlank(comentario.getTexto())) {
            return new ResponseEntity<>(new Mensaje("El comentario no puede estar vacío"), HttpStatus.BAD_REQUEST);
        }
        if (!tareaService.existsById(idTarea) || !usuarioService.existsById(idUsuario)) {
            return new ResponseEntity<>(new Mensaje("La tarea o el usuario no existen"), HttpStatus.BAD_REQUEST);
        }

        Tarea tarea = tareaService.findById(idTarea).get();
        Usuario usuario = usuarioService.findById(idUsuario).get();

        comentario.setTarea(tarea);
        comentario.setAutor(usuario);
        tareaService.saveComentario(comentario);

        return new ResponseEntity<>(new Mensaje("Comentario añadido correctamente"), HttpStatus.CREATED);
    }
}