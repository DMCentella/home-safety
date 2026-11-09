package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReporteKPIDTO {
  private int total;
  private int programadas;
  private int completadas;
  private int canceladas;
}