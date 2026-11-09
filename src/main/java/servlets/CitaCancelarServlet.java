package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import interfaces.CitaDAO;
import interfaces.PacienteDAO;
import mantenimiento.CitaDAOImpl;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

@WebServlet(name="CitaCancelar", urlPatterns={"/citas/cancelar"})
public class CitaCancelarServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final CitaDAO citaDAO = new CitaDAOImpl();
  private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("idUsuario") == null) {
      resp.sendRedirect(req.getContextPath()+"/login.jsp");
      return;
    }

    try {
      String idCitaStr = req.getParameter("id_cita");
      String motivo = req.getParameter("motivo");
      if (idCitaStr == null || idCitaStr.isBlank()) {
        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Falta id de cita");
        return;
      }
      int idCita = Integer.parseInt(idCitaStr);

      Integer idUsuario = (Integer) session.getAttribute("idUsuario");
      PacienteDTO p = pacienteDAO.buscarPorIdUsuario(idUsuario);
      if (p == null) {
        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=No tienes perfil de paciente");
        return;
      }

      // Verificar que la cita pertenece al paciente
      if (!citaDAO.esDePaciente(idCita, p.getIdPaciente())) {
        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Cita no pertenece al paciente");
        return;
      }

      // Cambiar estado a Cancelada 
      citaDAO.actualizarEstado(idCita, "Cancelada",
          (motivo==null||motivo.isBlank())? null : motivo.trim());

      resp.sendRedirect(req.getContextPath()+"/mis-citas?msg=Cita cancelada");
    } catch (NumberFormatException ex) {
      resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Id de cita inválido");
    } catch (Exception e) {
      e.printStackTrace();
      resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Error interno: "+e.getMessage());
    }
  }
}
