package interfaces;

import java.util.List;

import models.MedicoDTO;

public interface MedicoDAO {
	 MedicoDTO obtener(int idMedico);
	  List<MedicoDTO> listarPorEspecialidad(Integer especialidadId); 
	  List<MedicoDTO> listarTodos();
}
