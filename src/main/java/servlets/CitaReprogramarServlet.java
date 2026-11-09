package servlets;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import interfaces.CitaDAO;
import interfaces.PacienteDAO;
import mantenimiento.CitaDAOImpl;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

/**
 * Servlet implementation class CitaReprogramarServlet
 */
@WebServlet(name = "CitaReprogramar", urlPatterns = { "/citas/reprogramar" })
public class CitaReprogramarServlet extends HttpServlet {
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
	      String idMedicoStr = req.getParameter("id_medico");
	      String fechaStr = req.getParameter("nueva_fecha_hora");
	      String motivo = req.getParameter("motivo");

	      if (idCitaStr==null || idMedicoStr==null || fechaStr==null || idCitaStr.isBlank() || idMedicoStr.isBlank() || fechaStr.isBlank()) {
	        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Datos incompletos para reprogramar");
	        return;
	      }

	      int idCita = Integer.parseInt(idCitaStr);
	      int idMedico = Integer.parseInt(idMedicoStr);
	      LocalDateTime nueva = LocalDateTime.parse(fechaStr);

	      if (nueva.isBefore(LocalDateTime.now())) {
	        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=No se puede reprogramar al pasado");
	        return;
	      }

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

	      // Chequear choque con otras citas del médico
	      if (citaDAO.existeChoque(idMedico, nueva)) {
	        resp.sendRedirect(req.getContextPath()+"/mis-citas?error=El médico ya tiene una cita en ese horario");
	        return;
	      }

	      // Reprogramar
	      citaDAO.reprogramar(idCita, nueva, (motivo==null||motivo.isBlank())? null : motivo.trim());
	      resp.sendRedirect(req.getContextPath()+"/mis-citas?msg=Cita reprogramada correctamente");
	    } catch (NumberFormatException | DateTimeParseException ex) {
	      resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Parámetros inválidos");
	    } catch (Exception e) {
	      e.printStackTrace();
	      resp.sendRedirect(req.getContextPath()+"/mis-citas?error=Error interno: "+e.getMessage());
	    }
	  }
	}