package servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Authentication filter to protect secured pages
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/home.jsp", 
    "/admin/*", 
    "/medico/*", 
    "/recepcion/*", 
    "/paciente/*",
    "/CitaCrearRecepcion",
    "/PacienteRegistrarManual",
    "/PacienteRegistra",
    "/PacienteVincular"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        boolean isLoggedIn = (session != null && session.getAttribute("idUsuario") != null);
        
        if (isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debe iniciar sesión para acceder");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}