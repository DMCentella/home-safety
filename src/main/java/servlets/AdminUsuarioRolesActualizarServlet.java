package servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.UsuarioDAo;
import mantenimiento.UsuarioDAOImpl;

/**
 * Servlet implementation class AdminUsuarioRolesActualizarServlet
 */
@WebServlet(name = "AdminUsuarioRolesActualizar", urlPatterns = { "/admin/gestiones/roles" })
public class AdminUsuarioRolesActualizarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final UsuarioDAo usuarioDAO = new UsuarioDAOImpl();
	@Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession s = req.getSession(false);
	    String rol = s==null? null : String.valueOf(s.getAttribute("rol"));
	    if (rol==null || !"ADMIN".equals(rol)) { resp.sendRedirect(req.getContextPath()+"/login.jsp"); return; }

	    try {
	      int idUsuario = Integer.parseInt(req.getParameter("id_usuario"));
	      String[] rolesArr = req.getParameterValues("roles");
	      List<String> roles = rolesArr==null ? List.of() : Arrays.asList(rolesArr);
	      usuarioDAO.actualizarRoles(idUsuario, roles);
	      resp.sendRedirect(req.getContextPath()+"/admin/gestiones?msg=Roles actualizados");
	    } catch (Exception e) {
	      e.printStackTrace();
	      resp.sendRedirect(req.getContextPath()+"/admin/gestiones?error=No se pudo actualizar roles");
	    }
	  }
	}

