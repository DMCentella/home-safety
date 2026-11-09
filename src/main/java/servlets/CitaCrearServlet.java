package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.CitaDAO;
import interfaces.PacienteDAO;
import mantenimiento.CitaDAOImpl;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

@WebServlet(name = "CitaCrear", urlPatterns = {"/citas/crear"})
public class CitaCrearServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final CitaDAO citaDAO = new CitaDAOImpl();
    private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Obtener parámetros del formulario
            String idMedicoStr = req.getParameter("id_medico");
            String fechaHoraStr = req.getParameter("fecha_hora");
            String motivoConsulta = req.getParameter("motivo_consulta");

            // Validaciones básicas
            if (isBlank(idMedicoStr) || isBlank(fechaHoraStr)) {
                req.setAttribute("error", "Médico, fecha y hora son requeridos");
                req.getRequestDispatcher("/reservar").forward(req, resp);
                return;
            }

            // Obtener ID del paciente
            Integer idUsuario = (Integer) session.getAttribute("idUsuario");
            PacienteDTO paciente = pacienteDAO.buscarPorIdUsuario(idUsuario);
            
            if (paciente == null) {
                req.setAttribute("error", "Debe completar su perfil de paciente primero");
                req.getRequestDispatcher("/reservar").forward(req, resp);
                return;
            }

            // Parsear parámetros
            int idMedico = Integer.parseInt(idMedicoStr);
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);

            // Validar que la fecha sea futura
            if (fechaHora.isBefore(LocalDateTime.now())) {
                req.setAttribute("error", "No se pueden crear citas en el pasado");
                req.getRequestDispatcher("/reservar").forward(req, resp);
                return;
            }

            // Verificar conflictos de horario
            if (citaDAO.existeChoque(idMedico, fechaHora)) {
                req.setAttribute("error", "El médico ya tiene una cita programada en esa fecha y hora");
                req.getRequestDispatcher("/reservar").forward(req, resp);
                return;
            }

            // Crear la cita
            String motivo = isBlank(motivoConsulta) ? "Consulta general" : motivoConsulta.trim();
            int idCita = citaDAO.crearParaPacientes(paciente.getIdPaciente(), idMedico, fechaHora, motivo);

            if (idCita > 0) {
                // Redirigir con mensaje de éxito a "Mis Citas"
                resp.sendRedirect(req.getContextPath() + "/mis-citas?creada=" + idCita);
            } else {
                req.setAttribute("error", "No se pudo crear la cita. Intente nuevamente.");
                req.getRequestDispatcher("/reservar").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID de médico inválido");
            req.getRequestDispatcher("/reservar").forward(req, resp);
        } catch (DateTimeParseException e) {
            req.setAttribute("error", "Formato de fecha u hora inválido");
            req.getRequestDispatcher("/reservar").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error interno del sistema: " + e.getMessage());
            req.getRequestDispatcher("/reservar").forward(req, resp);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}