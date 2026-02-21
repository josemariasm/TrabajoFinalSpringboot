package progresa.gestionproyectos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comentario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "texto")
    private String texto;

    @ManyToOne
    @JoinColumn(name = "tarea_id")
    @JsonIgnore
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
}