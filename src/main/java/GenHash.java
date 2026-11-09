import org.mindrot.jbcrypt.BCrypt;

public class GenHash {
  public static void main(String[] args) {
    // Verificar si el hash actual funciona con "123456"
    String currentHash = "$2a$10$C6UzMDM.H6dfI/f/IKcEeO..g8eqfiwgCb5.Ki0dDQ6RzPlp9gZfu";
    String password = "123456";
    
    System.out.println("=== VERIFICACIÓN DE HASH ===");
    System.out.println("Password probando: " + password);
    System.out.println("Hash en BD: " + currentHash);
    
    boolean matches = BCrypt.checkpw(password, currentHash);
    System.out.println("¿Hash actual válido para '123456'?: " + matches);
    
    if (!matches) {
        System.out.println("\n=== GENERANDO NUEVO HASH ===");
        String newHash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println("Nuevo hash para '123456': " + newHash);
        
        // Verificar el nuevo hash
        boolean newMatches = BCrypt.checkpw(password, newHash);
        System.out.println("¿Nuevo hash válido?: " + newMatches);
        
        System.out.println("\n=== COMANDO SQL PARA ACTUALIZAR ===");
        System.out.println("UPDATE usuarios SET password_hash = '" + newHash + "' WHERE username IN ('admin1', 'medico1', 'paciente1', 'recep1');");
    }
  }
}