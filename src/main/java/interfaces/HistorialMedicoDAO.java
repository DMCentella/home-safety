package interfaces;

import java.util.List;

import models.HistorialMedicoDTO;

public interface HistorialMedicoDAO {
	int crearHistorial(int idPaciente, int idCita, String notas, String archivosAdjuntos);
	
    HistorialMedicoDTO obtenerPorCita(int idCita);
    void actualizar(int idHistorial, String notas, String adjuntos);
    List<HistorialMedicoDTO> listarPorPaciente(int idPaciente);
}
