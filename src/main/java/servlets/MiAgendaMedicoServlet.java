package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.CitaDAO;
import interfaces.HistorialMedicoDAO;
import interfaces.UsuarioDAo;
import mantenimiento.CitaDAOImpl;
import mantenimiento.HistorialMedicoDAOImpl;
import mantenimiento.UsuarioDAOImpl;
import models.HistorialMedicoDTO;

@WebServlet(name = "MiAgendaMedico", urlPatterns = { "/medico/mi-agenda" })
public class MiAgendaMedicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final CitaDAO citaDAO = new CitaDAOImpl();
	private final HistorialMedicoDAO historialDAO = new HistorialMedicoDAOImpl();
	private final UsuarioDAo usuarioDAO = new UsuarioDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession s = req.getSession(false);
			if (s==null || !"MEDICO".equals(String.valueOf(s.getAttribute("rol")))) {
				resp.sendRedirect(req.getContextPath()+"/login.jsp"); 
				return;
			}

			Integer idUsuario = (Integer)s.getAttribute("idUsuario");
			Integer idMedico  = (Integer)s.getAttribute("idMedico");
			
			System.out.println("DEBUG: idUsuario = " + idUsuario + ", idMedico = " + idMedico);
			
			if (idMedico == null) {
				idMedico = usuarioDAO.idMedicoPorUsuario(idUsuario);
				s.setAttribute("idMedico", idMedico);
				System.out.println("DEBUG: idMedico obtenido de BD = " + idMedico);
			}
			
			if (idMedico == null) { 
				req.setAttribute("error", "Usuario no vinculado a médico");
				req.getRequestDispatcher("/mi-agenda.jsp").forward(req, resp);
				return; 
			}

			LocalDate fecha = null;
			try { 
				fecha = LocalDate.parse( Optional.ofNullable(req.getParameter("fecha")).orElse("") ); 
			} catch(Exception ignore){}
			if (fecha == null) fecha = LocalDate.now();

			System.out.println("DEBUG: Consultando agenda para médico " + idMedico + " en fecha " + fecha);

			CitaDAO.KPIs kpis = citaDAO.kpisDiaMedico(idMedico, fecha);
			List<CitaDAO.CitaAgenda> citas = citaDAO.citasDiaMedico(idMedico, fecha);

			System.out.println("DEBUG: KPIs = " + kpis + ", Citas encontradas = " + (citas != null ? citas.size() : "null"));

			Map<Integer, HistorialMedicoDTO> historialesMap = new HashMap<>();
			for (CitaDAO.CitaAgenda c : citas) {
				HistorialMedicoDTO h = historialDAO.obtenerPorCita(c.idCita);
				if (h != null) historialesMap.put(c.idCita, h);
			}

			req.setAttribute("fecha", fecha.toString());
			req.setAttribute("kpis", kpis);
			req.setAttribute("citas", citas);
			req.setAttribute("historialesMap", historialesMap);

			req.getRequestDispatcher("/mi-agenda.jsp").forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Error interno: " + e.getMessage());
			req.getRequestDispatcher("/mi-agenda.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		if (s==null || !"MEDICO".equals(String.valueOf(s.getAttribute("rol")))) { resp.sendError(403); return; }

		Integer idMedico = (Integer)s.getAttribute("idMedico");
		String fecha = Optional.ofNullable(req.getParameter("fecha")).orElse(LocalDate.now().toString());
		String action = req.getParameter("action");

		try {
			if ("estado".equals(action)) {
				int idCita = Integer.parseInt(req.getParameter("id_cita"));
				String estado = req.getParameter("estado");
				if (!citaDAO.esDeMedico(idCita, idMedico)) { resp.sendError(403); return; }
				citaDAO.actualizarEstado(idCita, estado, null);
				resp.sendRedirect(req.getContextPath()+"/medico/mi-agenda?fecha="+fecha+"&ok=estado");
				return;
			}

			if ("guardar_historial".equals(action)) {
				int idCita = Integer.parseInt(req.getParameter("id_cita"));
				String notas = req.getParameter("notas");
				int idHist = 0;
				try { idHist = Integer.parseInt(req.getParameter("id_historial")); } catch(Exception ignore){}

				if (!citaDAO.esDeMedico(idCita, idMedico)) { resp.sendError(403); return; }

				if (idHist > 0) {
					historialDAO.actualizar(idHist, notas, null);
				} else {
					Integer idPaciente = citaDAO.pacienteIdPorCita(idCita);
					if (idPaciente == null) throw new IllegalStateException("No se encontró paciente de la cita");
					historialDAO.crearHistorial(idPaciente, idCita, notas, null);
				}
				resp.sendRedirect(req.getContextPath()+"/medico/mi-agenda?fecha="+fecha+"&ok=hist");
				return;
			}

			resp.sendError(400, "Acción no reconocida");
		} catch(Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath()+"/medico/mi-agenda?fecha="+fecha+"&err=1");
		}
	}
}