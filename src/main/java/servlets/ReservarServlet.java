package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.EspecialidadDAO;
import interfaces.MedicoDAO;
import mantenimiento.EspecialidadDAOImpl;
import mantenimiento.MedicoDAOImpl;
import models.EspecialidadDTO;
import models.MedicoDTO;

@WebServlet(name = "Reservar", urlPatterns = {"/reservar"})
public class ReservarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAOImpl();
    private final MedicoDAO medicoDAO = new MedicoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debe iniciar sesión para reservar una cita");
            return;
        }

        try {
            // Cargar especialidades para el combobox
            List<EspecialidadDTO> especialidades = especialidadDAO.listarToda();
            System.out.println("DEBUG: Especialidades cargadas: " + especialidades.size());
            
            // Cargar todos los médicos (se filtrarán por JavaScript según especialidad)
            List<MedicoDTO> medicos = medicoDAO.listarPorEspecialidad(null);
            System.out.println("DEBUG: Médicos cargados: " + medicos.size());

            // Pasar datos a la vista
            req.setAttribute("especialidades", especialidades);
            req.setAttribute("medicos", medicos);

            // Forward a la página de reservar
            req.getRequestDispatcher("/reservar.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error interno del sistema: " + e.getMessage());
            req.getRequestDispatcher("/reservar.jsp").forward(req, resp);
        }
    }
}