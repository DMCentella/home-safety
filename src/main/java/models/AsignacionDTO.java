package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionDTO {
    private int id;
    private int pacienteId;
    private int medicoId;
    private String turno; 
    private Timestamp fechaRegistro;
}