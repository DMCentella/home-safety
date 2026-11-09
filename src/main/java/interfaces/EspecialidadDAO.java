package interfaces;

import java.util.List;

import models.EspecialidadDTO;

public interface EspecialidadDAO {
	List<EspecialidadDTO> listarToda();
	 public EspecialidadDTO obtener(int id);

}
