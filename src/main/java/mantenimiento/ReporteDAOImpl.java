package mantenimiento;

import conexion.MySQLConexion;
import interfaces.ReporteDAO;
import models.ReporteKPIDTO;
import models.ReporteRowDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAOImpl implements ReporteDAO {

    @Override
    public ReporteKPIDTO kpiS(LocalDate d, LocalDate h, Integer espId, String est) {
        ReporteKPIDTO k = new ReporteKPIDTO(0, 0, 0, 0);
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_reportes_kpis(?,?,?,?)}")) {

            if (d == null) cs.setNull(1, Types.DATE); else cs.setDate(1, Date.valueOf(d));
            if (h == null) cs.setNull(2, Types.DATE); else cs.setDate(2, Date.valueOf(h));
            if (espId == null) cs.setNull(3, Types.INTEGER); else cs.setInt(3, espId);
            if (est == null || est.isBlank()) cs.setNull(4, Types.VARCHAR); else cs.setString(4, est);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    k.setTotal(rs.getInt("total"));
                    k.setProgramadas(rs.getInt("programadas"));
                    k.setCompletadas(rs.getInt("completadas"));
                    k.setCanceladas(rs.getInt("canceladas"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error KPIs reporte", e);
        }
        return k;
    }

    @Override
    public int contar(LocalDate d, LocalDate h, Integer espId, String est) {
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_reportes_contar(?,?,?,?)}")) {

            if (d == null) cs.setNull(1, Types.DATE); else cs.setDate(1, Date.valueOf(d));
            if (h == null) cs.setNull(2, Types.DATE); else cs.setDate(2, Date.valueOf(h));
            if (espId == null) cs.setNull(3, Types.INTEGER); else cs.setInt(3, espId);
            if (est == null || est.isBlank()) cs.setNull(4, Types.VARCHAR); else cs.setString(4, est);

            try (ResultSet rs = cs.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error contar reporte", e);
        }
    }

    @Override
    public List<ReporteRowDTO> listar(LocalDate desde,
                                      LocalDate hasta,
                                      Integer especialidadId,
                                      String estado,
                                      int limit,
                                      int offset) {
        List<ReporteRowDTO> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall("{CALL sp_reportes_listar(?,?,?,?,?,?)}")) {

            if (desde == null) cs.setNull(1, Types.DATE); else cs.setDate(1, Date.valueOf(desde));
            if (hasta == null) cs.setNull(2, Types.DATE); else cs.setDate(2, Date.valueOf(hasta));
            if (especialidadId == null) cs.setNull(3, Types.INTEGER); else cs.setInt(3, especialidadId);
            if (estado == null || estado.isBlank()) cs.setNull(4, Types.VARCHAR); else cs.setString(4, estado);
            cs.setInt(5, limit);
            cs.setInt(6, offset);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    ReporteRowDTO r = new ReporteRowDTO();
                    r.setFecha(rs.getString("fecha"));
                    r.setHora(rs.getString("hora"));
                    r.setPaciente(rs.getString("paciente"));
                    r.setDoctor(rs.getString("doctor"));
                    r.setEspecialidad(rs.getString("especialidad"));
                    r.setEstado(rs.getString("estado"));
                    out.add(r);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error listar reporte", e);
        }
        return out;
    }
}
