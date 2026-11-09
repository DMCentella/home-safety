package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import interfaces.EspecialidadDAO;
import interfaces.MedicoDAO;
import mantenimiento.EspecialidadDAOImpl;
import mantenimiento.MedicoDAOImpl;
import models.EspecialidadDTO;
import models.MedicoDTO;

@WebServlet(name = "TestData", urlPatterns = {"/test-data"})
public class TestDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Data</title></head><body>");
        out.println("<h1>Prueba de Datos</h1>");
        
        try {
            // Probar especialidades
            EspecialidadDAO especialidadDAO = new EspecialidadDAOImpl();
            List<EspecialidadDTO> especialidades = especialidadDAO.listarToda();
            
            out.println("<h2>Especialidades (" + especialidades.size() + ")</h2>");
            out.println("<ul>");
            for (EspecialidadDTO esp : especialidades) {
                out.println("<li>ID: " + esp.getId() + " - " + esp.getNombre() + "</li>");
            }
            out.println("</ul>");
            
            // Probar médicos
            MedicoDAO medicoDAO = new MedicoDAOImpl();
            List<MedicoDTO> medicos = medicoDAO.listarPorEspecialidad(null);
            
            out.println("<h2>Médicos (" + medicos.size() + ")</h2>");
            out.println("<ul>");
            for (MedicoDTO med : medicos) {
                out.println("<li>ID: " + med.getIdMedico() + " - " + med.getNombre() + " " + med.getApellido() + 
                           " - Esp ID: " + med.getEspecialidadId() + " - Turno: " + med.getTurno() + "</li>");
            }
            out.println("</ul>");
            
        } catch (Exception e) {
            out.println("<h2 style='color: red;'>ERROR:</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("</body></html>");
    }
}