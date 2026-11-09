package servlets;

import interfaces.PacienteDAO;
import mantenimiento.PacienteDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="PacientesActualizar", urlPatterns={"/pacientes/actualizar"})
public class PacientesActualizarServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      int id = Integer.parseInt(req.getParameter("id_paciente"));
      String tel     = req.getParameter("telefono");
      String email   = req.getParameter("email");
      String seguro  = req.getParameter("seguro_medico");
      String dirRes  = req.getParameter("direccion_residencia");
      String depRes  = req.getParameter("depto_residencia");
      String provRes = req.getParameter("prov_residencia");
      String distRes = req.getParameter("dist_residencia");
      String numDep  = req.getParameter("num_departamento");
      String urban   = req.getParameter("urbanizacion");

  
      pacienteDAO.actualizarBasicos(
          id, tel, email, seguro, dirRes, depRes, provRes, distRes, numDep, urban
      );

      String q  = nz(req.getParameter("q"));
      String sz = nz(req.getParameter("size"));
      String pg = nz(req.getParameter("page"));
      String back = req.getContextPath()+"/pacientes?msg=Paciente actualizado";
      if (!q.isEmpty())  back += "&q="+enc(q);
      if (!sz.isEmpty()) back += "&size="+enc(sz);
      if (!pg.isEmpty()) back += "&page="+enc(pg);

      resp.sendRedirect(back);

    } catch (Exception e) {
      e.printStackTrace();
      resp.sendRedirect(req.getContextPath()+"/pacientes?error=No se pudo actualizar");
    }
  }

  private static String nz(String s){ return s==null?"":s; }
  private static String enc(String s){ try{ return java.net.URLEncoder.encode(s,"UTF-8"); }catch(Exception e){ return s; } }
}
