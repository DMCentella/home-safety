package interfaces;

import java.util.List;

import models.PacienteDTO;

public interface PacienteDAO {
	 Integer findIdByUsuario(Integer idUsuario);
	  int creaDesdeElUsuario(PacienteDTO p, Integer idUsuario);
	  PacienteDTO obtener(int idPaciente);
	  int crearManual(PacienteDTO p);
	    PacienteDTO buscarPorDni(String dni);
	    PacienteDTO buscarPorIdUsuario(int idUsuario);
	    int vincularUsuarioPorDni(String dni, int idUsuario);
	    void actualizarContacto(int idPaciente, String telefono, String email);
	    List<PacienteDTO> buscar(String filtro, int limit, int offset); // opcional para recep.
	    int contar(String filtro);  
	    void actualizarBasicos(int idPaciente,
                String telefono, String email, String seguroMedico,
                String direccionResidencia, String deptoResidencia,
                String provResidencia, String distResidencia,
                String numDepartamento, String urbanizacion);
	  }