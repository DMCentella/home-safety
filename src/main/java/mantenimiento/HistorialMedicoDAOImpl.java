package mantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexion.MySQLConexion;
import interfaces.HistorialMedicoDAO;
import models.HistorialMedicoDTO;

public class HistorialMedicoDAOImpl implements HistorialMedicoDAO {

    @Override
    public int crearHistorial(int idPaciente, int idCita, String notas, String adjuntos) {
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_historial_crear(?,?,?,?)}")) {
            cs.setInt(1, idPaciente);
            cs.setInt(2, idCita);
            if (notas==null || notas.isBlank()) cs.setNull(3, Types.LONGVARCHAR); else cs.setString(3, notas);
            if (adjuntos==null || adjuntos.isBlank()) cs.setNull(4, Types.VARCHAR); else cs.setString(4, adjuntos);
            try (ResultSet rs = cs.executeQuery()){
                rs.next(); return rs.getInt("id_historial");
            }
        } catch (Exception e){ throw new RuntimeException("Error crear historial", e); }
    }

    // ⬇️ NUEVOS
    @Override
    public HistorialMedicoDTO obtenerPorCita(int idCita) {
        String sql = "SELECT * FROM historial_medico WHERE id_cita=? ORDER BY fecha_registro DESC LIMIT 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()){
                if (!rs.next()) return null;
                HistorialMedicoDTO h = new HistorialMedicoDTO();
                h.setIdHistorial(rs.getInt("id_historial"));
                h.setIdPaciente(rs.getInt("id_paciente"));
                h.setIdCita(rs.getInt("id_cita"));
                h.setNotas(rs.getString("notas"));
                h.setArchivosAdjuntos(rs.getString("archivos_adjuntos"));
                h.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                return h;
            }
        } catch (Exception e){ throw new RuntimeException("Error obtener historial por cita", e); }
    }

    @Override
    public void actualizar(int idHistorial, String notas, String adjuntos) {
        String sql = "UPDATE historial_medico SET notas=?, archivos_adjuntos=? WHERE id_historial=?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, (notas==null||notas.isBlank())? null : notas);
            ps.setString(2, (adjuntos==null||adjuntos.isBlank())? null : adjuntos);
            ps.setInt(3, idHistorial);
            ps.executeUpdate();
        } catch (Exception e){ throw new RuntimeException("Error actualizar historial", e); }
    }

    @Override
    public List<HistorialMedicoDTO> listarPorPaciente(int idPaciente) {
        List<HistorialMedicoDTO> out = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico WHERE id_paciente=? ORDER BY fecha_registro DESC";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    HistorialMedicoDTO h = new HistorialMedicoDTO();
                    h.setIdHistorial(rs.getInt("id_historial"));
                    h.setIdPaciente(rs.getInt("id_paciente"));
                    h.setIdCita(rs.getInt("id_cita"));
                    h.setNotas(rs.getString("notas"));
                    h.setArchivosAdjuntos(rs.getString("archivos_adjuntos"));
                    h.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    out.add(h);
                }
            }
        } catch (Exception e){ throw new RuntimeException("Error listar historial por paciente", e); }
        return out;
    }
}
