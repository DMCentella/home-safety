package mantenimiento;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import conexion.MySQLConexion;
import interfaces.PacienteDAO;
import models.PacienteDTO;

public class PacienteDAOImpl implements PacienteDAO {

	@Override
	public Integer findIdByUsuario(Integer idUsuario) {
		try (Connection cn = MySQLConexion.getConexion();
				CallableStatement cs = cn.prepareCall("{CALL sp_pacientes_find_id_by_usuario(?)}")) {
			cs.setInt(1, idUsuario);
			try (ResultSet rs = cs.executeQuery()) {
				return rs.next() ? rs.getInt(1) : null;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error find paciente by usuario", e);
		}
	}

	@Override
	public int creaDesdeElUsuario(PacienteDTO p, Integer idUsuario) {
		try (Connection cn = MySQLConexion.getConexion();
				CallableStatement cs = cn.prepareCall(
						"{CALL sp_pacientes_crear_desde_usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
			int i = 1;
			cs.setString(i++, p.getDni());
			cs.setString(i++, p.getNombre());
			cs.setString(i++, p.getApellido());
			cs.setDate(i++, new java.sql.Date(p.getFechaNacimiento().getTime()));
			cs.setString(i++, p.getGenero());
			cs.setString(i++, p.getTelefono());
			cs.setString(i++, p.getEmail());
			cs.setString(i++, p.getSeguroMedico());
			cs.setString(i++, p.getDeptoResidencia());
			cs.setString(i++, p.getProvResidencia());
			cs.setString(i++, p.getDistResidencia());
			cs.setString(i++, p.getDireccionResidencia());
			cs.setString(i++, p.getNumDepartamento());
			cs.setString(i++, p.getUrbanizacion());
			cs.setString(i++, p.getDeptoNacimiento());
			cs.setString(i++, p.getProvNacimiento());
			cs.setString(i++, p.getDistNacimiento());
			cs.setString(i++, p.getDireccionNacimiento());
			if (idUsuario == null)
				cs.setNull(i++, Types.INTEGER);
			else
				cs.setInt(i++, idUsuario);
			cs.registerOutParameter(i, Types.INTEGER);
			cs.execute();
			return cs.getInt(i);
		} catch (Exception e) {
			throw new RuntimeException("Error crear paciente desde usuario", e);
		}
	}

	@Override
	public PacienteDTO obtener(int idPaciente) {
		try (Connection cn = MySQLConexion.getConexion();
				PreparedStatement ps = cn.prepareStatement("SELECT * FROM pacientes WHERE id_paciente=?")) {
			ps.setInt(1, idPaciente);
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					return null;
				PacienteDTO p = new PacienteDTO();
				p.setIdPaciente(rs.getInt("id_paciente"));
				p.setDni(rs.getString("dni"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
				p.setGenero(rs.getString("genero"));
				p.setTelefono(rs.getString("telefono"));
				p.setEmail(rs.getString("email"));
				p.setSeguroMedico(rs.getString("seguro_medico"));
				p.setDeptoResidencia(rs.getString("depto_residencia"));
				p.setProvResidencia(rs.getString("prov_residencia"));
				p.setDistResidencia(rs.getString("dist_residencia"));
				p.setDireccionResidencia(rs.getString("direccion_residencia"));
				p.setNumDepartamento(rs.getString("num_departamento"));
				p.setUrbanizacion(rs.getString("urbanizacion"));
				p.setDeptoNacimiento(rs.getString("depto_nacimiento"));
				p.setProvNacimiento(rs.getString("prov_nacimiento"));
				p.setDistNacimiento(rs.getString("dist_nacimiento"));
				p.setDireccionNacimiento(rs.getString("direccion_nacimiento"));
				p.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				try {
					p.setIdUsuario((Integer) rs.getObject("id_usuario"));
				} catch (Exception ignore) {
				}
				return p;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error obtener paciente", e);
		}
	}

	@Override
	public int crearManual(PacienteDTO p) {
		try (Connection cn = MySQLConexion.getConexion();
				CallableStatement cs = cn
						.prepareCall("{CALL sp_paciente_crear_manual(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {

			cargarParamsPacienteSinUsuario(cs, p);
			cs.execute();

			// Obtener el resultado
			try (ResultSet rs = cs.getResultSet()) {
				if (rs.next()) {
					return rs.getInt("id_paciente");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error crearManual", e);
		}
		return 0;
	}

	@Override
	public PacienteDTO buscarPorDni(String dni) {
		String sql = "SELECT * FROM pacientes WHERE dni = ?";
		try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return map(rs);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error buscarPorDni", e);
		}
		return null;
	}

	@Override
	public PacienteDTO buscarPorIdUsuario(int idUsuario) {
		String sql = "SELECT id_paciente, dni, nombre, apellido FROM pacientes WHERE id_usuario = ?";
		try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					PacienteDTO p = new PacienteDTO();
					p.setIdPaciente(rs.getInt("id_paciente"));
					p.setDni(rs.getString("dni"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					return p;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error buscarPorIdUsuario", e);
		}
		return null;
	}

	@Override
	public int vincularUsuarioPorDni(String dni, int idUsuario) {
		try (Connection cn = MySQLConexion.getConexion();
				CallableStatement cs = cn.prepareCall("{CALL sp_paciente_vincular_usuario(?,?)}")) {
			cs.setString(1, dni);
			cs.setInt(2, idUsuario);
			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next())
					return rs.getInt("afectados"); // 1 si actualizó
			}
		} catch (Exception e) {
			throw new RuntimeException("Error vincularUsuarioPorDni", e);
		}
		return 0;
	}

	@Override
	public void actualizarContacto(int idPaciente, String telefono, String email) {
		String sql = "UPDATE pacientes SET telefono=?, email=? WHERE id_paciente=?";
		try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
			ps.setString(1, nullIfBlank(telefono));
			ps.setString(2, nullIfBlank(email));
			ps.setInt(3, idPaciente);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Error actualizarContacto", e);
		}
	}

	@Override
	public List<PacienteDTO> buscar(String filtro, int limit, int offset) {
		String sql = """
				SELECT * FROM pacientes
				WHERE dni LIKE ? OR nombre LIKE ? OR apellido LIKE ?
				ORDER BY fecha_registro DESC
				LIMIT ? OFFSET ?""";
		List<PacienteDTO> out = new ArrayList<>();
		try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
			String like = "%" + (filtro == null ? "" : filtro.trim()) + "%";
			ps.setString(1, like);
			ps.setString(2, like);
			ps.setString(3, like);
			ps.setInt(4, limit);
			ps.setInt(5, offset);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					out.add(map(rs));
			}
		} catch (Exception e) {
			throw new RuntimeException("Error buscar pacientes", e);
		}
		return out;
	}


	private static void cargarParamsPacienteSinUsuario(CallableStatement cs, PacienteDTO p) throws SQLException {
		int i = 1;
		cs.setString(i++, p.getDni());
		cs.setString(i++, p.getNombre());
		cs.setString(i++, p.getApellido());
		cs.setDate(i++, new java.sql.Date(p.getFechaNacimiento().getTime()));
		cs.setString(i++, p.getGenero());
		cs.setString(i++, nullIfBlank(p.getTelefono()));
		cs.setString(i++, nullIfBlank(p.getEmail()));
		cs.setString(i++, nullIfBlank(p.getSeguroMedico()));
		cs.setString(i++, nullIfBlank(p.getDeptoResidencia()));
		cs.setString(i++, nullIfBlank(p.getProvResidencia()));
		cs.setString(i++, nullIfBlank(p.getDistResidencia()));
		cs.setString(i++, p.getDireccionResidencia());
		cs.setString(i++, nullIfBlank(p.getNumDepartamento()));
		cs.setString(i++, nullIfBlank(p.getUrbanizacion()));
		cs.setString(i++, nullIfBlank(p.getDeptoNacimiento()));
		cs.setString(i++, nullIfBlank(p.getProvNacimiento()));
		cs.setString(i++, nullIfBlank(p.getDistNacimiento()));
		cs.setString(i++, nullIfBlank(p.getDireccionNacimiento()));
	}

	private static String nullIfBlank(String s) {
		return (s == null || s.isBlank()) ? null : s.trim();
	}

	private static PacienteDTO map(ResultSet rs) throws SQLException {
		PacienteDTO p = new PacienteDTO();
		p.setIdPaciente(rs.getInt("id_paciente"));
		p.setIdUsuario((Integer) rs.getObject("id_usuario"));
		p.setDni(rs.getString("dni"));
		p.setNombre(rs.getString("nombre"));
		p.setApellido(rs.getString("apellido"));
		p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
		p.setGenero(rs.getString("genero"));
		p.setTelefono(rs.getString("telefono"));
		p.setEmail(rs.getString("email"));
		p.setSeguroMedico(rs.getString("seguro_medico"));
		p.setDeptoResidencia(rs.getString("depto_residencia"));
		p.setProvResidencia(rs.getString("prov_residencia"));
		p.setDistResidencia(rs.getString("dist_residencia"));
		p.setDireccionResidencia(rs.getString("direccion_residencia"));
		p.setNumDepartamento(rs.getString("num_departamento"));
		p.setUrbanizacion(rs.getString("urbanizacion"));
		p.setDeptoNacimiento(rs.getString("depto_nacimiento"));
		p.setProvNacimiento(rs.getString("prov_nacimiento"));
		p.setDistNacimiento(rs.getString("dist_nacimiento"));
		p.setDireccionNacimiento(rs.getString("direccion_nacimiento"));
		p.setFechaRegistro(rs.getTimestamp("fecha_registro"));
		return p;
	}

	@Override
	public int contar(String filtro) {
		String sql = "SELECT COUNT(*) FROM pacientes WHERE dni LIKE ? OR nombre LIKE ? OR apellido LIKE ?";
		String like = "%" + (filtro == null ? "" : filtro.trim()) + "%";
		try (var cn = MySQLConexion.getConexion(); var ps = cn.prepareStatement(sql)) {
			ps.setString(1, like);
			ps.setString(2, like);
			ps.setString(3, like);
			try (var rs = ps.executeQuery()) {
				return rs.next() ? rs.getInt(1) : 0;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error contar pacientes", e);
		}
	}

	@Override
	public void actualizarBasicos(int idPaciente, String telefono, String email, String seguroMedico,
			String direccionResidencia, String deptoResidencia, String provResidencia, String distResidencia,
			String numDepartamento, String urbanizacion) {

		String sql = """
				  UPDATE pacientes SET
				    telefono=?,
				    email=?,
				    seguro_medico=?,
				    direccion_residencia=?,
				    depto_residencia=?,
				    prov_residencia=?,
				    dist_residencia=?,
				    num_departamento=?,
				    urbanizacion=?
				  WHERE id_paciente=?
				""";
		try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
			int i = 1;
			ps.setString(i++, nullIfBlank(telefono));
			ps.setString(i++, nullIfBlank(email));
			ps.setString(i++, nullIfBlank(seguroMedico));
			ps.setString(i++, nullIfBlank(direccionResidencia));
			ps.setString(i++, nullIfBlank(deptoResidencia));
			ps.setString(i++, nullIfBlank(provResidencia));
			ps.setString(i++, nullIfBlank(distResidencia));
			ps.setString(i++, nullIfBlank(numDepartamento));
			ps.setString(i++, nullIfBlank(urbanizacion));
			ps.setInt(i, idPaciente);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Error actualizarBasicos", e);
		}
	}
}