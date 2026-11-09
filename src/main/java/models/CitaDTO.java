package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDTO {
    private int idCita;
    private int idPaciente;
    private int idMedico;
    private LocalDateTime fechaHora;
    private String estado; // Programada, Cancelada, Completada
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private Timestamp fechaRegistro;
    
    private String medicoNombre;
    private String medicoApellido;
    private String especialidadNombre;
    
    public java.util.Date getFechaHoraDate() {
        return fechaHora == null ? null : java.sql.Timestamp.valueOf(fechaHora);
    }
}