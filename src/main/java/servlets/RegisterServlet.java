package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;

import interfaces.UsuarioDAo;
import mantenimiento.UsuarioDAOImpl;

@WebServlet(name = "Register", urlPatterns = {"/register"})

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UsuarioDAo usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String email    = req.getParameter("email");
        String pass     = req.getParameter("password");
        String confirm  = req.getParameter("confirm");
        String tipo     = req.getParameter("tipo"); 

        if (username==null || email==null || pass==null || confirm==null ||
            username.isBlank() || email.isBlank() || pass.isBlank() || confirm.isBlank()) {
            req.setAttribute("error", "Completa todos los campos");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        if (!pass.equals(confirm)) {
            req.setAttribute("error", "Las contraseñas no coinciden");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        if (usuarioDAO.existsUsername(username)) {
            req.setAttribute("error", "El nombre de usuario ya existe");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        if (usuarioDAO.existsEmail(email)) {
            req.setAttribute("error", "El correo ya está registrado");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        String hash = BCrypt.hashpw(pass, BCrypt.gensalt(10));
        int idUsuario = usuarioDAO.crearUsuario(username, email, hash);

        // Asigna rol por defecto 
        String rol = ("MEDICO".equalsIgnoreCase(tipo) || "ADMIN".equalsIgnoreCase(tipo)) ? tipo.toUpperCase() : "PACIENTE";
        usuarioDAO.asignarRol(idUsuario, rol);

        resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=Cuenta creada. Ya puedes iniciar sesión.");
    }
}
