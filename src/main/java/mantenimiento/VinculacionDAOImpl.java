// src/main/java/mantenimiento/VinculacionDAOImpl.java
package mantenimiento;

import conexion.MySQLConexion;
import interfaces.VinculacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VinculacionDAOImpl implements VinculacionDAO {

    @Override
    public void vincularMedicoAUsuario(int idMedico, int idUsuario) {
        String clearUser = "UPDATE medicos SET id_usuario=NULL WHERE id_usuario=?";
        String setMedico = "UPDATE medicos SET id_usuario=? WHERE id_medico=?";
        try (Connection cn = MySQLConexion.getConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement p1 = cn.prepareStatement(clearUser)) {
                p1.setInt(1, idUsuario);
                p1.executeUpdate();
            }
            try (PreparedStatement p2 = cn.prepareStatement(setMedico)) {
                p2.setInt(1, idUsuario);
                p2.setInt(2, idMedico);
                p2.executeUpdate();
            }
            cn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error vincularMedicoAUsuario", e);
        }
    }

    @Override
    public void limpiarVinculoMedicoDeUsuario(int idUsuario) {
        String sql = "UPDATE medicos SET id_usuario=NULL WHERE id_usuario=?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error limpiarVinculoMedicoDeUsuario", e);
        }
    }

    @Override
    public void desvincularMedicoDeUsuario(int idMedico) {
        String sql = "UPDATE medicos SET id_usuario=NULL WHERE id_medico=?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error desvincularMedicoDeUsuario", e);
        }
    }

    @Override
    public void vincularPacienteAUsuario(int idPaciente, int idUsuario) {
        String clearUser = "UPDATE pacientes SET id_usuario=NULL WHERE id_usuario=?";
        String setPac    = "UPDATE pacientes SET id_usuario=? WHERE id_paciente=?";
        try (Connection cn = MySQLConexion.getConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement p1 = cn.prepareStatement(clearUser)) {
                p1.setInt(1, idUsuario);
                p1.executeUpdate();
            }
            try (PreparedStatement p2 = cn.prepareStatement(setPac)) {
                p2.setInt(1, idUsuario);
                p2.setInt(2, idPaciente);
                p2.executeUpdate();
            }
            cn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error vincularPacienteAUsuario", e);
        }
    }

    @Override
    public void limpiarVinculoPacienteDeUsuario(int idUsuario) {
        String sql = "UPDATE pacientes SET id_usuario=NULL WHERE id_usuario=?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error limpiarVinculoPacienteDeUsuario", e);
        }
    }

    @Override
    public void desvincularPacienteDeUsuario(int idPaciente) {
        String sql = "UPDATE pacientes SET id_usuario=NULL WHERE id_paciente=?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error desvincularPacienteDeUsuario", e);
        }
    }
}
