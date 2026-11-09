package servlets;

import java.io.IOException;
import java.util.List;
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
import models.CitaDTO;
import models.PacienteDTO;

@WebServlet(name = "MisCitas", urlPatterns = {"/mis-citas"})
public class MisCitasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final CitaDAO citaDAO = new CitaDAOImpl();
    private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debe iniciar sesión para ver sus citas");
            return;
        }

        try {
            // Obtener ID del usuario desde la sesión
            Integer idUsuario = (Integer) session.getAttribute("idUsuario");
            System.out.println("DEBUG: idUsuario = " + idUsuario);
            
            // Buscar el paciente asociado al usuario
            PacienteDTO paciente = pacienteDAO.buscarPorIdUsuario(idUsuario);
            System.out.println("DEBUG: paciente encontrado = " + (paciente != null ? paciente.getIdPaciente() : "null"));
            
            if (paciente == null) {
                req.setAttribute("error", "No tienes un perfil de paciente registrado");
                req.setAttribute("sinPerfil", true);
                req.getRequestDispatcher("/mis-citas.jsp").forward(req, resp);
                return;
            }

            // Obtener todas las citas del paciente 
            List<CitaDTO> citas = citaDAO.listarPorPaciente(
                paciente.getIdPaciente(), 
                null, // sin filtro de fecha desde
                null, // sin filtro de fecha hasta  
                null  // sin filtro de estado
            );
            
            System.out.println("DEBUG: Total citas encontradas = " + citas.size());
            if (!citas.isEmpty()) {
                System.out.println("DEBUG: Primera cita = " + citas.get(0).getIdCita() + " - " + citas.get(0).getMotivoConsulta());
            }

            req.setAttribute("citas", citas);
            req.setAttribute("paciente", paciente);

            req.getRequestDispatcher("/mis-citas.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR en MisCitasServlet: " + e.getMessage());
            req.setAttribute("error", "Error interno del sistema: " + e.getMessage());
            req.getRequestDispatcher("/mis-citas.jsp").forward(req, resp);
        }
    }
}