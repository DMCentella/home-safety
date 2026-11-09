package interfaces;

import java.time.LocalDate;
import java.util.List;


import models.ReporteKPIDTO;
import models.ReporteRowDTO;

public interface ReporteDAO {
	
	ReporteKPIDTO kpiS(LocalDate desde, LocalDate hasta, Integer especialidadId, String estado);
	  List<ReporteRowDTO> listar(LocalDate desde, LocalDate hasta, Integer especialidadId, String estado, int limit, int offset);
	  int contar(LocalDate desde, LocalDate hasta, Integer especialidadId, String estado);
	}