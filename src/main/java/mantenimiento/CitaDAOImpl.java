package mantenimiento;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import conexion.MySQLConexion;
import interfaces.CitaDAO;
import models.CitaDTO;

public class CitaDAOImpl implements CitaDAO {

	  private CitaDTO map(ResultSet rs) throws Exception {
	    CitaDTO c = new CitaDTO();
	    c.setIdCita(rs.getInt("id_cita"));
	    c.setIdPaciente(rs.getInt("id_paciente"));
	    c.setIdMedico(rs.getInt("id_medico"));
	    c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
	    c.setEstado(rs.getString("estado"));
	    c.setMotivoConsulta(rs.getString("motivo_consulta"));
	    c.setDiagnostico(rs.getString("diagnostico"));
	    c.setTratamiento(rs.getString("tratamiento"));
	    c.setFechaRegistro(rs.getTimestamp("fecha_registro"));
	    
	    try {
	      c.setMedicoNombre(rs.getString("medicoNombre"));
	      c.setMedicoApellido(rs.getString("medicoApellido"));
	      c.setEspecialidadNombre(rs.getString("especialidadNombre"));
	    } catch (SQLException ignore) {
	      System.out.println("Campos adicionales de médico no encontrados en ResultSet");
	    }
	    
	    return c;
	  }

	  @Override public boolean existeChoque(int idMedico, LocalDateTime fechaHora) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_existe_choque(?,?,?)}")) {
	      cs.setInt(1, idMedico);
	      cs.setTimestamp(2, Timestamp.valueOf(fechaHora));
	      cs.registerOutParameter(3, Types.TINYINT);
	      cs.execute();
	      return cs.getInt(3) == 1;
	    } catch (Exception e){ throw new RuntimeException("Error existeChoque", e); }
	  }

	  @Override public int crear(int idPaciente, int idMedico, LocalDateTime fechaHora, String motivo) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_crear(?,?,?,?,?)}")) {
	      cs.setInt(1, idPaciente);
	      cs.setInt(2, idMedico);
	      cs.setTimestamp(3, Timestamp.valueOf(fechaHora));
	      cs.setString(4, motivo);
	      cs.registerOutParameter(5, Types.INTEGER);
	      cs.execute();
	      return cs.getInt(5);
	    } catch (Exception e){ throw new RuntimeException("Error crear cita", e); }
	  }

	  @Override public List<CitaDTO> listarPorPaciente(int idPaciente, LocalDate desde, LocalDate hasta, String estado) {
	    List<CitaDTO> out = new ArrayList<>();
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_listar_por_paciente(?,?,?,?)}")) {
	      cs.setInt(1, idPaciente);
	      if (desde==null) cs.setNull(2, Types.DATE); else cs.setDate(2, java.sql.Date.valueOf(desde));
	      if (hasta==null) cs.setNull(3, Types.DATE); else cs.setDate(3, java.sql.Date.valueOf(hasta));
	      if (estado==null || estado.isBlank()) cs.setNull(4, Types.VARCHAR); else cs.setString(4, estado);
	      try (ResultSet rs = cs.executeQuery()){
	        while (rs.next()){
	          out.add(map(rs));
	        }
	      }
	    } catch (Exception e){ throw new RuntimeException("Error listar citas paciente", e); }
	    return out;
	  }

	  @Override public void reprogramar(int idCita, LocalDateTime nuevaFechaHora, String motivo) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_reprogramar(?,?,?)}")) {
	      cs.setInt(1, idCita);
	      cs.setTimestamp(2, Timestamp.valueOf(nuevaFechaHora));
	      if (motivo==null || motivo.isBlank()) cs.setNull(3, Types.VARCHAR); else cs.setString(3, motivo);
	      cs.execute();
	    } catch (Exception e){ throw new RuntimeException("Error reprogramar", e); }
	  }

	  @Override public void actualizarEstado(int idCita, String estado, String motivo) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_actualizar_estado(?,?,?)}")) {
	      cs.setInt(1, idCita);
	      cs.setString(2, estado);
	      if (motivo==null || motivo.isBlank()) cs.setNull(3, Types.VARCHAR); else cs.setString(3, motivo);
	      cs.execute();
	    } catch (Exception e){ throw new RuntimeException("Error actualizar estado", e); }
	  }

	  @Override public KPIs kpisDiaMedico(int idMedico, LocalDate fecha) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_kpis_dia_medico(?,?)}")) {
	      cs.setInt(1, idMedico);
	      cs.setDate(2, java.sql.Date.valueOf(fecha));
	      try (ResultSet rs = cs.executeQuery()){
	        if (rs.next()) return new KPIs(rs.getInt("programadas"), rs.getInt("completadas"), rs.getInt("canceladas"));
	        return new KPIs(0,0,0);
	      }
	    } catch (Exception e){ throw new RuntimeException("Error KPIs día médico", e); }
	  }

	  @Override public List<CitaAgenda> citasDiaMedico(int idMedico, LocalDate fecha) {
	    List<CitaAgenda> out = new ArrayList<>();
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_citas_dia_medico(?,?)}")) {
	      cs.setInt(1, idMedico);
	      cs.setDate(2, java.sql.Date.valueOf(fecha));
	      try (ResultSet rs = cs.executeQuery()){
	        while (rs.next()){
	          CitaAgenda a = new CitaAgenda();
	          a.idCita = rs.getInt("id_cita");
	          a.hora   = rs.getString("hora");
	          a.pacienteNombre = rs.getString("paciente_nombre");
	          a.pacienteApellido = rs.getString("paciente_apellido");
	          a.especialidadNombre = rs.getString("especialidad_nombre");
	          a.estado = rs.getString("estado");
	          a.motivoConsulta = rs.getString("motivo_consulta");
	          out.add(a);
	        }
	      }
	    } catch (Exception e){ throw new RuntimeException("Error citas día médico", e); }
	    return out;
	  }
	  @Override
	    public int crearParaPacientes(int idPaciente, int idMedico, LocalDateTime fechaHora, String motivo) {
	        try (Connection cn = MySQLConexion.getConexion();
	             CallableStatement cs = cn.prepareCall("{CALL sp_cita_crear_para_paciente(?,?,?,?)}")) {
	            cs.setInt(1, idPaciente);
	            cs.setInt(2, idMedico);
	            cs.setTimestamp(3, Timestamp.valueOf(fechaHora));
	            cs.setString(4, motivo);
	            try (ResultSet rs = cs.executeQuery()) {
	                if (rs.next()) return rs.getInt("id_cita");
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Error crearParaPaciente", e);
	        }
	        return 0;
	    }
	  
	  @Override
	  public boolean esDePaciente(int idCita, int idPaciente) {
	    String sql = "SELECT 1 FROM citas WHERE id_cita=? AND id_paciente=? LIMIT 1";
	    try (Connection cn = MySQLConexion.getConexion();
	         PreparedStatement ps = cn.prepareStatement(sql)) {
	      ps.setInt(1, idCita);
	      ps.setInt(2, idPaciente);
	      try (ResultSet rs = ps.executeQuery()) {
	        return rs.next();
	      }
	    } catch (Exception e) {
	      throw new RuntimeException("Error esDePaciente", e);
	    }
	  }
	  
	  @Override
	  public boolean esDeMedico(int idCita, int idMedico) {
	      String sql = "SELECT 1 FROM citas WHERE id_cita=? AND id_medico=? LIMIT 1";
	      try (Connection cn = MySQLConexion.getConexion();
	           PreparedStatement ps = cn.prepareStatement(sql)) {
	          ps.setInt(1, idCita);
	          ps.setInt(2, idMedico);
	          try (ResultSet rs = ps.executeQuery()){ return rs.next(); }
	      } catch (Exception e){ throw new RuntimeException("Error esDeMedico", e); }
	  }

	  @Override
	  public Integer pacienteIdPorCita(int idCita) {
	      String sql = "SELECT id_paciente FROM citas WHERE id_cita=?";
	      try (Connection cn = MySQLConexion.getConexion();
	           PreparedStatement ps = cn.prepareStatement(sql)) {
	          ps.setInt(1, idCita);
	          try (ResultSet rs = ps.executeQuery()){ return rs.next()? rs.getInt(1) : null; }
	      } catch (Exception e){ throw new RuntimeException("Error pacienteIdPorCita", e); }
	  }
	}