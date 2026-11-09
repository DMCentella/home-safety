package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private Timestamp fechaRegistro;
}