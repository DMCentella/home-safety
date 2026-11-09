package mantenimiento;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import conexion.MySQLConexion;
import interfaces.AsignacionDAO;

public class AsignacionDAOImpl implements AsignacionDAO {
	  @Override public Integer buscarOCrear(int pacienteId, int medicoId, String turno) {
	    try (Connection cn = MySQLConexion.getConexion();
	         CallableStatement cs = cn.prepareCall("{CALL sp_asignaciones_buscar_o_crear(?,?,?,?)}")) {
	      cs.setInt(1, pacienteId);
	      cs.setInt(2, medicoId);
	      cs.setString(3, turno);
	      cs.registerOutParameter(4, Types.INTEGER);
	      cs.execute();
	      return (Integer) cs.getObject(4);
	    } catch (Exception e){ throw new RuntimeException("Error asignación buscar/crear", e); }
	  }
	}