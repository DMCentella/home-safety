package mantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexion.MySQLConexion;
import interfaces.UsuarioDAo;
import models.UsuarioDTO;

public class UsuarioDAOImpl implements UsuarioDAo {

    @Override
    public UsuarioDTO findByUser(String usernameOrEmail) {
        String sql = "SELECT * FROM usuarios WHERE username=? OR email=? LIMIT 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                UsuarioDTO u = new UsuarioDTO();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getInt("estado"));
                u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                return u;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error findByUser", e);
        }
    }

    @Override
    public List<String> rolesDeUsuario(int idUsuario) {
        List<String> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_usuario_roles(?)}")) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error rolesDeUsuario", e);
        }
        return out;
    }

    @Override
    public Integer idMedicoPorUsuario(int idUsuario) {
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_usuario_id_medico_por_usuario(?)}")) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error idMedicoPorUsuario", e);
        }
    }

    @Override
    public Integer idPacientePorUsuario(int idUsuario) {
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_usuario_id_paciente_por_usuario(?)}")) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error idPacientePorUsuario", e);
        }
    }

    @Override
    public boolean existsUsername(String username) {
        String sql = "SELECT 1 FROM usuarios WHERE username=? LIMIT 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) {
            throw new RuntimeException("Error existsUsername", e);
        }
    }

    @Override
    public boolean existsEmail(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email=? LIMIT 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) {
            throw new RuntimeException("Error existsEmail", e);
        }
    }

    @Override
    public int crearUsuario(String username, String email, String passwordHash) {
        String sql = "INSERT INTO usuarios(username,email,password_hash,estado) VALUES(?,?,?,1)";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, passwordHash);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            throw new RuntimeException("No se devolvió id_usuario");
        } catch (Exception e) {
            throw new RuntimeException("Error crearUsuario", e);
        }
    }

    @Override
    public void asignarRol(int idUsuario, String rolNombre) {
        String sql = "INSERT IGNORE INTO usuario_roles(id_usuario, id_rol) " +
                     "SELECT ?, r.id FROM roles r WHERE r.nombre=? LIMIT 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, rolNombre);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error asignarRol", e);
        }
    }
    
    @Override
    public List<UsuarioDTO> listarTodos() {
        String sql = "SELECT id_usuario, username, password_hash, email, estado, fecha_registro " +
                     "FROM usuarios ORDER BY username";
        List<UsuarioDTO> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UsuarioDTO u = new UsuarioDTO();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getInt("estado"));
                u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                u.setRoles(rolesDeUsuario(u.getIdUsuario()));
                u.setIdMedico(idMedicoPorUsuario(u.getIdUsuario()));
                u.setIdPaciente(idPacientePorUsuario(u.getIdUsuario()));
                out.add(u);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error listarTodos usuarios", e);
        }
        return out;
    }

    @Override
    public void actualizarRoles(int idUsuario, List<String> roles) {
        String del = "DELETE FROM usuario_roles WHERE id_usuario=?";
        String ins = "INSERT INTO usuario_roles(id_usuario, id_rol) " +
                     "SELECT ?, r.id FROM roles r WHERE r.nombre=?";

        try (Connection cn = MySQLConexion.getConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psDel = cn.prepareStatement(del)) {
                psDel.setInt(1, idUsuario);
                psDel.executeUpdate();
            }
            if (roles != null && !roles.isEmpty()) {
                try (PreparedStatement psIns = cn.prepareStatement(ins)) {
                    for (String rol : roles) {
                        psIns.setInt(1, idUsuario);
                        psIns.setString(2, rol);
                        psIns.addBatch();
                    }
                    psIns.executeBatch();
                }
            }
            cn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error actualizarRoles", e);
        }
    }
}
