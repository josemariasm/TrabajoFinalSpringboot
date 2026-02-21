package progresa.gestionproyectos.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.gestionproyectos.dto.Mensaje;
import progresa.gestionproyectos.entity.Proyecto;
import progresa.gestionproyectos.entity.Tarea;
import progresa.gestionproyectos.entity.Usuario;
import progresa.gestionproyectos.service.ProyectoService;
import progresa.gestionproyectos.service.TareaService;
import progresa.gestionproyectos.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaService tareaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto) {
        if (StringUtils.isBlank(proyecto.getNombre())) {
            return new ResponseEntity<>(new Mensaje("El nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (proyectoService.existsByNombre(proyecto.getNombre())) {
            return new ResponseEntity<>(new Mensaje("Ya existe un proyecto con ese nombre"), HttpStatus.BAD_REQUEST);
        }

        proyecto.setEstadoGlobal("Activo");
        proyectoService.save(proyecto);
        return new ResponseEntity<>(new Mensaje("Proyecto creado correctamente"), HttpStatus.CREATED);
    }

    @PostMapping("/{idProyecto}/asignarUsuarios")
    public ResponseEntity<?> asignarUsuarios(@PathVariable Long idProyecto, @RequestBody List<Long> usuariosIds) {
        if (!proyectoService.existsById(idProyecto)) {
            return new ResponseEntity<>(new Mensaje("No existe el proyecto con el ID: " + idProyecto), HttpStatus.BAD_REQUEST);
        }
        if (usuariosIds == null || usuariosIds.isEmpty()) {
            return new ResponseEntity<>(new Mensaje("Debe proporcionar al menos un usuario"), HttpStatus.BAD_REQUEST);
        }

        List<Usuario> usuarios = usuarioService.findAllById(usuariosIds);
        if (usuarios.size() != usuariosIds.size()) {
            return new ResponseEntity<>(new Mensaje("Algunos de los usuarios proporcionados no existen"), HttpStatus.BAD_REQUEST);
        }

        Proyecto proyecto = proyectoService.findById(idProyecto).get();
        proyecto.getUsuarios().addAll(usuarios);
        proyectoService.save(proyecto);

        return new ResponseEntity<>(new Mensaje("Usuarios asignados al proyecto correctamente"), HttpStatus.OK);
    }

    @GetMapping("/{idProyecto}/avance")
    public ResponseEntity<?> consultarAvance(@PathVariable Long idProyecto) {
        if (!proyectoService.existsById(idProyecto)) {
            return new ResponseEntity<>(new Mensaje("No existe el proyecto"), HttpStatus.BAD_REQUEST);
        }

        List<Tarea> tareas = tareaService.getTareasPorProyecto(idProyecto);
        Map<String, Integer> avance = new HashMap<>();

        for (Tarea tarea : tareas) {
            String nombreEstado = (tarea.getEstado() != null) ? tarea.getEstado().getNombre() : "Sin estado";
            avance.put(nombreEstado, avance.getOrDefault(nombreEstado, 0) + 1);
        }

        return new ResponseEntity<>(avance, HttpStatus.OK);
    }
}