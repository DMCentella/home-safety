package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String username;
    private String passwordHash;
    private String email;
    private Integer estado;            
    private Timestamp fechaRegistro;
    private String rol;
    
    private List<String> roles = new ArrayList<>();
    private Integer idMedico;   
    private Integer idPaciente; 

    public String getRolPrincipal() {
        if (roles == null) return null;
        List<String> prioridad = Arrays.asList("ADMIN","RECEPCIONISTA","MEDICO","PACIENTE");
        for (String p : prioridad) if (roles.contains(p)) return p;
        return roles.isEmpty() ? null : roles.get(0);
    }

    public boolean hasRole(String r) {
        return roles != null && roles.contains(r);
    }
}
