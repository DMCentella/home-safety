package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import interfaces.PacienteDAO;
import mantenimiento.PacienteDAOImpl;
import models.PacienteDTO;

@WebServlet(name = "PacienteRegistrarManual", urlPatterns = { "/PacienteRegistrarManual" })
public class PacienteRegistrarManualServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final PacienteDAO pacienteDAO = new PacienteDAOImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (!tieneAcceso(req.getSession(false))) {
      resp.sendRedirect(req.getContextPath() + "/login.jsp");
      return;
    }
    resp.sendRedirect(req.getContextPath() + "/recepcion/pacientes-registrar.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (!tieneAcceso(req.getSession(false))) {
      resp.sendRedirect(req.getContextPath() + "/login.jsp");
      return;
    }

    try {
      // Validar campos requeridos
      String dni = req.getParameter("dni");
      String nombre = req.getParameter("nombre");
      String apellido = req.getParameter("apellido");
      String fechaNacimiento = req.getParameter("fecha_nacimiento");
      String genero = req.getParameter("genero");

      // Validaciones básicas
      if (isBlank(dni) || isBlank(nombre) || isBlank(apellido) || 
          isBlank(fechaNacimiento) || isBlank(genero)) {
        req.setAttribute("error", "DNI, nombre, apellido, fecha de nacimiento y género son obligatorios.");
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
        return;
      }

      // Validar formato del DNI
      dni = dni.trim();
      if (!dni.matches("\\d{8}")) {
        req.setAttribute("error", "El DNI debe tener exactamente 8 dígitos");
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
        return;
      }

      // Validar nombres y apellidos
      if (!nombre.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+") || 
          !apellido.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+")) {
        req.setAttribute("error", "Los nombres y apellidos solo pueden contener letras y espacios");
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
        return;
      }

      // Validar género
      if (!genero.equals("M") && !genero.equals("F")) {
        req.setAttribute("error", "Debe seleccionar un género válido");
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
        return;
      }

      PacienteDTO existente = pacienteDAO.buscarPorDni(dni);
      if (existente != null) {
        req.setAttribute("error", "Ya existe un paciente con el DNI: " + dni);
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
        return;
      }

      // crear
      PacienteDTO p = buildFromRequest(req);
      int idPaciente = pacienteDAO.crearManual(p);

      if (idPaciente > 0) {
        req.setAttribute("msg", "Paciente creado exitosamente. DNI: " + dni + " - ID: " + idPaciente);
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
      } else {
        req.setAttribute("error", "No se pudo crear el paciente. Intente nuevamente.");
        req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
      }
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", "Error interno del sistema: " + e.getMessage());
      req.getRequestDispatcher("/paciente-nuevo.jsp").forward(req, resp);
    }
  }


  private static boolean tieneAcceso(HttpSession s) {
    if (s == null) return false;
    Object rol = s.getAttribute("rol");
    if (rol == null) return false;
    String r = rol.toString();
    return "RECEPCIONISTA".equals(r) || "ADMIN".equals(r);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private PacienteDTO buildFromRequest(HttpServletRequest req) throws ParseException {
    PacienteDTO p = new PacienteDTO();
    p.setDni(req.getParameter("dni").trim());
    p.setNombre(req.getParameter("nombre").trim());
    p.setApellido(req.getParameter("apellido").trim());

    String fechaNacStr = req.getParameter("fecha_nacimiento");
    if (!isBlank(fechaNacStr)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        p.setFechaNacimiento(sdf.parse(fechaNacStr.trim()));
      } catch (Exception e) {
        throw new ParseException("Formato de fecha inválido", 0);
      }
    }

    p.setGenero(req.getParameter("genero").trim());
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

  private static String trimOrNull(String v) {
    return (v == null || v.trim().isEmpty()) ? null : v.trim();
  }
}