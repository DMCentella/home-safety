package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.VinculacionDAO;
import mantenimiento.VinculacionDAOImpl;

/**
 * Servlet implementation class AdminVincularMedicoServlet
 */
@WebServlet(name = "AdminVincularMedico", urlPatterns = { "/admin/gestiones/vincular-medico" })
public class AdminVincularMedicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VinculacionDAO vinculacionDAO = new VinculacionDAOImpl();
	@Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession s = req.getSession(false);
	    String rol = s==null? null : String.valueOf(s.getAttribute("rol"));
	    if (rol==null || !"ADMIN".equals(rol)) { resp.sendRedirect(req.getContextPath()+"/login.jsp"); return; }

	    try {
	      int idUsuario = Integer.parseInt(req.getParameter("id_usuario"));
	      String medStr = req.getParameter("id_medico");

	      if (medStr==null || medStr.isBlank()) {
	        vinculacionDAO.limpiarVinculoMedicoDeUsuario(idUsuario);
	      } else {
	        vinculacionDAO.vincularMedicoAUsuario(Integer.parseInt(medStr), idUsuario);
	      }
	      resp.sendRedirect(req.getContextPath()+"/admin/gestiones?msg=Vínculo de médico actualizado");
	    } catch (Exception e) {
	      e.printStackTrace();
	      resp.sendRedirect(req.getContextPath()+"/admin/gestiones?error=No se pudo vincular médico");
	    }
	  }
	}
