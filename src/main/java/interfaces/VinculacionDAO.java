package interfaces;

public interface VinculacionDAO {
	
    void vincularMedicoAUsuario(int idMedico, int idUsuario);
    void limpiarVinculoMedicoDeUsuario(int idUsuario);
    void desvincularMedicoDeUsuario(int idMedico);
    void vincularPacienteAUsuario(int idPaciente, int idUsuario);
    void limpiarVinculoPacienteDeUsuario(int idUsuario);
    void desvincularPacienteDeUsuario(int idPaciente);

}
