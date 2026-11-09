package mantenimiento;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import conexion.MySQLConexion;
import interfaces.MedicoDAO;
import models.MedicoDTO;

public class MedicoDAOImpl implements MedicoDAO {

	  private MedicoDTO map(ResultSet rs) throws Exception {
	    MedicoDTO m = new MedicoDTO();
	    m.setIdMedico(rs.getInt("id_medico"));
	    m.setNombre(rs.getString("nombre"));
	    m.setApellido(rs.getString("apellido"));
	    m.setEspecialidadId(rs.getInt("especialidad_id"));
	    m.setHorarioDisponible(rs.getString("horario_disponible"));
	    m.setTurno(rs.getString("turno"));
	    m.setFechaRegistro(rs.getTimestamp("fecha_registro"));
	    try { m.setIdUsuario((Integer) rs.getObject("id_usuario")); } catch(Exception ignore){}
	    return m;
	  }

	  @Override public MedicoDTO obtener(int idMedico) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_medicos_obtener(?)}")) {
	      cs.setInt(1, idMedico);
	      try (ResultSet rs = cs.executeQuery()){
	        return rs.next()? map(rs) : null;
	      }
	    } catch (Exception e){ 
	      e.printStackTrace(); 
	      throw new RuntimeException("Error obtener médico", e); 
	    }
	  }

	  @Override public List<MedicoDTO> listarPorEspecialidad(Integer especialidadId) {
	    List<MedicoDTO> out = new ArrayList<>();
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_medicos_listar_por_especialidad(?)}")) {
	      if (especialidadId==null) {
	        cs.setNull(1, Types.INTEGER); 
	      } else {
	        cs.setInt(1, especialidadId);
	      }
	      try (ResultSet rs = cs.executeQuery()){
	        while (rs.next()) {
	          out.add(map(rs));
	        }
	      }
	    } catch (Exception e){ 
	      e.printStackTrace(); 
	      throw new RuntimeException("Error listar médicos por especialidad", e); 
	    }
	    return out;
	  }
	  @Override
	  public List<MedicoDTO> listarTodos() {
	      List<MedicoDTO> out = new ArrayList<>();
	      String sql = "SELECT * FROM medicos ORDER BY apellido, nombre";
	      try (Connection cn = MySQLConexion.getConexion();
	           PreparedStatement ps = cn.prepareStatement(sql);
	           ResultSet rs = ps.executeQuery()) {
	          while (rs.next()) out.add(map(rs));
	      } catch (Exception e) {
	          throw new RuntimeException("Error listarTodos medicos", e);
	      }
	      return out;
	  }
	}