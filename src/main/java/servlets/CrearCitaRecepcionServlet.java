package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import interfaces.CitaDAO;
import interfaces.PacienteDAO;
import mantenimiento.CitaDAOImpl;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

/**
 * Servlet para crear citas desde recepción
 */
@WebServlet("/CrearCitaRecepcion")
public class CrearCitaRecepcionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CitaDAO citaDAO = new CitaDAOImpl();
	private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("DEBUG: Recibida petición POST en CrearCitaRecepcionServlet");
		
		try {
			// Input validation
			String idPacienteStr = req.getParameter("id_paciente");
			String idMedicoStr = req.getParameter("id_medico");
			String fechaHoraStr = req.getParameter("fecha_hora");
			String motivo = req.getParameter("motivo_consulta");

			System.out.println("DEBUG: Parámetros recibidos:");
			System.out.println("- id_paciente: " + idPacienteStr);
			System.out.println("- id_medico: " + idMedicoStr);
			System.out.println("- fecha_hora: " + fechaHoraStr);
			System.out.println("- motivo_consulta: " + motivo);

			if (idPacienteStr == null || idPacienteStr.trim().isEmpty() ||
				idMedicoStr == null || idMedicoStr.trim().isEmpty() ||
				fechaHoraStr == null || fechaHoraStr.trim().isEmpty() ||
				motivo == null || motivo.trim().isEmpty()) {
				
				req.setAttribute("error", "Todos los campos son requeridos");
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			int idPaciente;
			int idMedico;
			try {
				idPaciente = Integer.parseInt(idPacienteStr.trim());
				idMedico = Integer.parseInt(idMedicoStr.trim());
			} catch (NumberFormatException e) {
				req.setAttribute("error", "IDs de paciente o médico inválidos");
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			PacienteDTO paciente = pacienteDAO.obtener(idPaciente);
			if (paciente == null) {
				req.setAttribute("error", "Paciente no encontrado con el ID: " + idPaciente);
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			LocalDateTime fechaHora;
			try {
				fechaHora = LocalDateTime.parse(fechaHoraStr.trim());
			} catch (DateTimeParseException e) {
				req.setAttribute("error", "Formato de fecha u hora inválido");
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			if (fechaHora.isBefore(LocalDateTime.now())) {
				req.setAttribute("error", "No se pueden crear citas en el pasado");
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			if (citaDAO.existeChoque(idMedico, fechaHora)) {
				req.setAttribute("error", "El médico ya tiene una cita programada en esa fecha y hora");
				req.getRequestDispatcher("/reservar-manual").forward(req, resp);
				return;
			}

			int idCita = citaDAO.crear(idPaciente, idMedico, fechaHora, motivo.trim());
			System.out.println("DEBUG: Cita creada exitosamente con ID: " + idCita);

			resp.sendRedirect(req.getContextPath() + "/pacientes-buscar.jsp?creada=" + idCita);
			
		} catch (Exception e) {
	
			e.printStackTrace();
			req.setAttribute("error", "Error interno del sistema. Intente nuevamente.");
			req.getRequestDispatcher("/reservar-manual").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// Redirect GET requests to appointment creation page
		response.sendRedirect(request.getContextPath() + "/reservar-manual");
	}
}