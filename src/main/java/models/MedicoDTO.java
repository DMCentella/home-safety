package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTO {
    private int idMedico;
    private String nombre;
    private String apellido;
    private int especialidadId;
    private String horarioDisponible;
    private String turno; 
    private Timestamp fechaRegistro;
    private Integer idUsuario; 
}
