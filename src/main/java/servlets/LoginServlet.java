package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;

import interfaces.UsuarioDAo;
import mantenimiento.UsuarioDAOImpl;
import models.UsuarioDTO;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UsuarioDAo usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Usuario y contraseña son requeridos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        UsuarioDTO user = usuarioDAO.findByUser(username.trim());
        if (user == null || user.getEstado() != 1 || !BCrypt.checkpw(password, user.getPasswordHash())) {
            req.setAttribute("error", "Usuario o contraseña inválidos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        List<String> roles = usuarioDAO.rolesDeUsuario(user.getIdUsuario());

        HttpSession s = req.getSession(true);
        s.setAttribute("idUsuario", user.getIdUsuario());
        s.setAttribute("username", user.getUsername());
        s.setAttribute("roles", roles);
        s.setAttribute("rol", determinePrimaryRole(roles));

        
    
    Integer idMedico = null;
    Integer idPaciente = null;
    if (roles != null) {
        if (roles.contains("MEDICO")) {
            idMedico = usuarioDAO.idMedicoPorUsuario(user.getIdUsuario());
        }
        if (roles.contains("PACIENTE")) {
            idPaciente = usuarioDAO.idPacientePorUsuario(user.getIdUsuario());
        }
    }
    s.setAttribute("idMedico", idMedico);     
    s.setAttribute("idPaciente", idPaciente); 
    resp.sendRedirect(req.getContextPath() + "/home.jsp");
}

    private String determinePrimaryRole(List<String> roles) {
        if (roles.contains("ADMIN")) return "ADMIN";
        if (roles.contains("MEDICO")) return "MEDICO";
        if (roles.contains("RECEPCIONISTA")) return "RECEPCIONISTA";
        return "PACIENTE";
    }
}