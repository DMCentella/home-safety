package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.MedicoDAO;
import interfaces.UsuarioDAo;
import mantenimiento.MedicoDAOImpl;
import mantenimiento.UsuarioDAOImpl;
import models.MedicoDTO;
import models.UsuarioDTO;

/**
 * Servlet implementation class AdminGestionesServlet
 */
@WebServlet(name = "AdminGestiones", urlPatterns = { "/admin/gestiones" })
public class AdminGestionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UsuarioDAo usuarioDAO = new UsuarioDAOImpl();
	  private final MedicoDAO  medicoDAO  = new MedicoDAOImpl();

	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession s = req.getSession(false);
	    String rol = s==null? null : String.valueOf(s.getAttribute("rol"));
	    if (rol==null || !"ADMIN".equals(rol)) { resp.sendRedirect(req.getContextPath()+"/login.jsp"); return; }

	    List<UsuarioDTO> usuarios = usuarioDAO.listarTodos();
	    List<MedicoDTO> medicos   = medicoDAO.listarTodos();

	    req.setAttribute("usuarios", usuarios);
	    req.setAttribute("medicos", medicos);
	    req.getRequestDispatcher("/gestiones.jsp").forward(req, resp);
	  }
	
}
