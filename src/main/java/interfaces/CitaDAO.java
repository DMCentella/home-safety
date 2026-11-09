package interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import models.CitaDTO;

public interface CitaDAO {
	 boolean existeChoque(int idMedico, LocalDateTime fechaHora);
	  int crear(int idPaciente, int idMedico, LocalDateTime fechaHora, String motivo);
	  List<CitaDTO> listarPorPaciente(int idPaciente, LocalDate desde, LocalDate hasta, String estado);
	  void reprogramar(int idCita, LocalDateTime nuevaFechaHora, String motivo);
	  void actualizarEstado(int idCita, String estado, String motivo);
	  // Agenda
	  record KPIs(int programadas, int completadas, int canceladas){}
	  KPIs kpisDiaMedico(int idMedico, LocalDate fecha);
	  class CitaAgenda {
	    public int idCita; public String hora; public String pacienteNombre; public String pacienteApellido;
	    public String especialidadNombre; public String estado; public String motivoConsulta;
	  }
	  List<CitaAgenda> citasDiaMedico(int idMedico, LocalDate fecha);
	  
	  int crearParaPacientes(int idPaciente, int idMedico, LocalDateTime fechaHora, String motivo);
	  boolean esDePaciente(int idCita, int idPaciente);
	  
	  boolean esDeMedico(int idCita, int idMedico);
	    Integer pacienteIdPorCita(int idCita);
	    
	   
	 
	}