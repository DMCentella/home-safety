// src/main/java/servlets/ReservarManualServlet.java
package servlets;

import interfaces.PacienteDAO;
import interfaces.MedicoDAO;
import interfaces.EspecialidadDAO;
import mantenimiento.PacienteDAOImpl;
import mantenimiento.MedicoDAOImpl;
import mantenimiento.EspecialidadDAOImpl;
import models.PacienteDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ReservarManual", urlPatterns={"/reservar-manual"})
public class ReservarManualServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private final PacienteDAO pacienteDAO = new PacienteDAOImpl();
  private final MedicoDAO medicoDAO = new MedicoDAOImpl();
  private final EspecialidadDAO especialidadDAO = new EspecialidadDAOImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   
     HttpSession s = req.getSession(false);
    if (s==null || !"RECEPCIONISTA".equals(String.valueOf(s.getAttribute("rol"))) && !"ADMIN".equals(String.valueOf(s.getAttribute("rol")))) {
     resp.sendRedirect(req.getContextPath()+"/login.jsp");
      return;
     }

    String idStr = req.getParameter("pacienteId");
    if (idStr != null && !idStr.isBlank()) {
      try {
        int id = Integer.parseInt(idStr);
        PacienteDTO p = pacienteDAO.obtener(id);
        req.setAttribute("pacienteSel", p); 
      } catch(Exception ignore){}
    }

    // Cargar combos
    req.setAttribute("especialidades", especialidadDAO.listarToda());
    req.setAttribute("medicos", medicoDAO.listarPorEspecialidad(null)); 

  
    req.getRequestDispatcher("/Reservar-manual.jsp").forward(req, resp);
  }
}
