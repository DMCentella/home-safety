package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialMedicoDTO {
    private int idHistorial;
    private int idPaciente;
    private int idCita;
    private String notas;
    private String archivosAdjuntos;
    private Timestamp fechaRegistro;
}