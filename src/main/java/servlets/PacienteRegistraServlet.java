package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.PacienteDAO;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

@WebServlet(name = "PacienteRegistrar", urlPatterns = { "/PacienteRegistra" })
public class PacienteRegistraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Muestra el formulario de registro de paciente
        response.sendRedirect(request.getContextPath() + "/paciente-registrar.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Integer idUsuario = (session == null) ? null : (Integer) session.getAttribute("idUsuario");
        if (idUsuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Validar campos 
            String dni = req.getParameter("dni");
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String fechaNacimiento = req.getParameter("fecha_nacimiento");
            String genero = req.getParameter("genero");
            String direccionResidencia = req.getParameter("direccion_residencia");

            // Validaciones básicas
            if (isBlank(dni) || isBlank(nombre) || isBlank(apellido) || 
                isBlank(fechaNacimiento) || isBlank(genero) || isBlank(direccionResidencia)) {
                req.setAttribute("error", "DNI, nombre, apellido, fecha de nacimiento, género y dirección de residencia son requeridos");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }

            // Validar formato del DNI
            dni = dni.trim();
            if (!dni.matches("\\d{8}")) {
                req.setAttribute("error", "El DNI debe tener exactamente 8 dígitos");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }

            // Validar nombres y apellidos
            if (!nombre.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+") || 
                !apellido.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+")) {
                req.setAttribute("error", "Los nombres y apellidos solo pueden contener letras y espacios");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }

            // Validar género
            if (!genero.equals("M") && !genero.equals("F")) {
                req.setAttribute("error", "Debe seleccionar un género válido");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }

            if (pacienteDAO.buscarPorDni(dni) != null) {
                req.setAttribute("error", "Ya existe un paciente con el DNI: " + dni);
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }
            
            if (pacienteDAO.buscarPorIdUsuario(idUsuario) != null) {
                req.setAttribute("error", "Tu usuario ya está vinculado a un paciente.");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
                return;
            }

            PacienteDTO p = buildFromRequest(req);
            int idPaciente = pacienteDAO.creaDesdeElUsuario(p, idUsuario);

            if (idPaciente > 0) {
                session.setAttribute("idPaciente", idPaciente);
                req.setAttribute("msg", "Perfil de paciente creado exitosamente");
                resp.sendRedirect(req.getContextPath() + "/home.jsp?perfil_ok=1");
            } else {
                req.setAttribute("error", "No se pudo crear el perfil del paciente. Intente nuevamente.");
                req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error interno del sistema: " + e.getMessage());
            req.getRequestDispatcher("/paciente-registrar.jsp").forward(req, resp);
        }
    }

    private PacienteDTO buildFromRequest(HttpServletRequest req) throws Exception {
        PacienteDTO p = new PacienteDTO();
        p.setDni(trimOrNull(req.getParameter("dni")));
        p.setNombre(trimOrNull(req.getParameter("nombre")));
        p.setApellido(trimOrNull(req.getParameter("apellido")));

        String fechaNacStr = req.getParameter("fecha_nacimiento");
        if (!isBlank(fechaNacStr)) {
            try {
                p.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacStr.trim()));
            } catch (Exception e) {
                throw new Exception("Formato de fecha inválido");
            }
        }

        p.setGenero(trimOrNull(req.getParameter("genero")));
        p.setTelefono(trimOrNull(req.getParameter("telefono")));
        p.setEmail(trimOrNull(req.getParameter("email")));
        p.setSeguroMedico(trimOrNull(req.getParameter("seguro_medico")));
        p.setDeptoResidencia(trimOrNull(req.getParameter("depto_residencia")));
        p.setProvResidencia(trimOrNull(req.getParameter("prov_residencia")));
        p.setDistResidencia(trimOrNull(req.getParameter("dist_residencia")));
        p.setDireccionResidencia(trimOrNull(req.getParameter("direccion_residencia")));
        p.setNumDepartamento(trimOrNull(req.getParameter("num_departamento")));
        p.setUrbanizacion(trimOrNull(req.getParameter("urbanizacion")));
        p.setDeptoNacimiento(trimOrNull(req.getParameter("depto_nacimiento")));
        p.setProvNacimiento(trimOrNull(req.getParameter("prov_nacimiento")));
        p.setDistNacimiento(trimOrNull(req.getParameter("dist_nacimiento")));
        p.setDireccionNacimiento(trimOrNull(req.getParameter("direccion_nacimiento")));
        return p;
    }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static String trimOrNull(String v){ return (v==null||v.isBlank())? null : v.trim(); }
}