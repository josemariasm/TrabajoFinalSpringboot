package progresa.gestionproyectos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TareaDTO {
    private String titulo;
    private String descripcion;
    private Long proyectoId;
}