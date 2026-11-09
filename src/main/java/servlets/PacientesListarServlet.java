// src/main/java/servlets/PacientesListarServlet.java
package servlets;

import interfaces.PacienteDAO;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name="PacientesListar", urlPatterns={"/pacientes"})
public class PacientesListarServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String q = trim(req.getParameter("q"));         
    int size = parseInt(req.getParameter("size"), 10);
    int page = Math.max(parseInt(req.getParameter("page"), 1), 1);
    int offset = (page - 1) * size;

    List<PacienteDTO> pacientes = pacienteDAO.buscar(q, size, offset);
    int total = pacienteDAO.contar(q);
    int pages = (int)Math.ceil(total / (double)size);

    req.setAttribute("q", q);
    req.setAttribute("size", size);
    req.setAttribute("page", page);
    req.setAttribute("total", total);
    req.setAttribute("pages", pages);
    req.setAttribute("pacientes", pacientes);

    req.getRequestDispatcher("/pacientes-buscar.jsp").forward(req, resp);
  }

  private static int parseInt(String s, int def){ try{ return Integer.parseInt(s); }catch(Exception e){ return def; } }
  private static String trim(String s){ return s==null? null : s.trim(); }
}
