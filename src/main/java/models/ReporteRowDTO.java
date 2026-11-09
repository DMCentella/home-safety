package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
	public class ReporteRowDTO {
	  private String fecha;
	  private String hora;
	  private String paciente;
	  private String doctor;
	  private String especialidad;
	  private String estado;
	}

