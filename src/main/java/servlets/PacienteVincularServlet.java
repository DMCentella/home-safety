package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.PacienteDAO;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

/**
 * Servlet implementation class PacienteVincularServlet
 */
@WebServlet(name = "PacienteVincular", urlPatterns = { "/PacienteVincular" })
public class PacienteVincularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// Redirect to patient linking form
		response.sendRedirect(request.getContextPath() + "/paciente/vincular.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		Integer idUsuario = (session == null) ? null : (Integer) session.getAttribute("idUsuario");
		
		if (idUsuario == null) { 
			resp.sendRedirect(req.getContextPath() + "/login.jsp"); 
			return; 
		}

		try {
			String dni = req.getParameter("dni");
			
			if (dni == null || dni.trim().isEmpty()) {
				req.setAttribute("error", "El DNI es requerido");
				req.getRequestDispatcher("/paciente/vincular.jsp").forward(req, resp);
				return;
			}

			PacienteDTO existente = pacienteDAO.buscarPorDni(dni.trim());
			if (existente == null || existente.getIdUsuario() != null) {
				req.setAttribute("error", "No se encontró paciente sin cuenta con ese DNI.");
				req.getRequestDispatcher("/paciente/vincular.jsp").forward(req, resp);
				return;
			}

			int afectados = pacienteDAO.vincularUsuarioPorDni(dni.trim(), idUsuario);
			if (afectados == 1) {
				session.setAttribute("idPaciente", existente.getIdPaciente());
				resp.sendRedirect(req.getContextPath() + "/home.jsp?vinculado=1");
			} else {
				req.setAttribute("error", "No se pudo vincular. Verifique el DNI.");
				req.getRequestDispatcher("/paciente/vincular.jsp").forward(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Error interno del sistema. Intente nuevamente.");
			req.getRequestDispatcher("/paciente/vincular.jsp").forward(req, resp);
		}
	}
}