package mantenimiento;


import java.sql.*;
import java.util.*;

import conexion.MySQLConexion;
import interfaces.EspecialidadDAO;
import models.EspecialidadDTO;

public class EspecialidadDAOImpl implements EspecialidadDAO {
  
  @Override 
  public List<EspecialidadDTO> listarToda() {
    List<EspecialidadDTO> out = new ArrayList<>();
    try (Connection cn = MySQLConexion.getConexion();
         CallableStatement cs = cn.prepareCall("{CALL sp_especialidades_listar()}")) {
      try (ResultSet rs = cs.executeQuery()){
        while (rs.next()){
          EspecialidadDTO e = new EspecialidadDTO();
          e.setId(rs.getInt("id")); 
          e.setNombre(rs.getString("nombre"));
          e.setDescripcion(rs.getString("descripcion"));
          e.setFechaRegistro(rs.getTimestamp("fecha_registro"));
          out.add(e);
        }
      }
    } catch (Exception e){ 
      e.printStackTrace(); 
      throw new RuntimeException("Error listar especialidades", e); 
    }
    return out;
  }

  @Override
  public EspecialidadDTO obtener(int id) {
    try (Connection cn = MySQLConexion.getConexion();
         CallableStatement cs = cn.prepareCall("{CALL sp_especialidades_obtener(?)}")) {
      
      cs.setInt(1, id);
      try (ResultSet rs = cs.executeQuery()) {
        if (rs.next()) {
          EspecialidadDTO e = new EspecialidadDTO();
          e.setId(rs.getInt("id")); 
          e.setNombre(rs.getString("nombre"));
          e.setDescripcion(rs.getString("descripcion"));
          e.setFechaRegistro(rs.getTimestamp("fecha_registro"));
          return e;
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); 
      throw new RuntimeException("Error al obtener especialidad", e);
    }
    return null;
  }
}