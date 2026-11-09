package interfaces;

import java.util.List;
import models.UsuarioDTO;

public interface UsuarioDAo {
    UsuarioDTO findByUser(String usernameOrEmail);
    List<String> rolesDeUsuario(int idUsuario);
    Integer idMedicoPorUsuario(int idUsuario);
    Integer idPacientePorUsuario(int idUsuario);

    boolean existsUsername(String username);
    boolean existsEmail(String email);
    int crearUsuario(String username, String email, String passwordHash);
    void asignarRol(int idUsuario, String rolNombre);
    
    List<UsuarioDTO> listarTodos();                         // lista base de usuarios
    void actualizarRoles(int idUsuario, List<String> roles); 
}
