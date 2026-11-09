package models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {
    private int idPaciente;
    private String dni;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String genero; // 'M' o 'F'
    private String telefono;
    private String email;
    private String seguroMedico;
    private String deptoResidencia;
    private String provResidencia;
    private String distResidencia;
    private String direccionResidencia;
    private String numDepartamento;
    private String urbanizacion;
    private String deptoNacimiento;
    private String provNacimiento;
    private String distNacimiento;
    private String direccionNacimiento;
    private Timestamp fechaRegistro;
    private Integer idUsuario; 
}
