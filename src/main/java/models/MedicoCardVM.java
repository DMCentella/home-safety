
package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MedicoCardVM {
    private int idMedico;
    private String nombre;
    private String apellido;
    private String especialidad;     
    private String horarioDisponible;
    private String turno;
}
