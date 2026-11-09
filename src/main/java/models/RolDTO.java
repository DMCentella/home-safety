package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private int id;
    private String nombre;       // ADMIN, MEDICO, PACIENTE, RECEPCIONISTA
    private String descripcion;
    private Timestamp fechaRegistro;
}