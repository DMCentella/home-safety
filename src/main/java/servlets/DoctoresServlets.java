package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import models.MedicoCardVM;
import models.MedicoDTO;

/**
 * Servlet implementation class DoctoresServlets
 */
@WebServlet(name = "Doctores", urlPatterns = { "/doctores" })
public class DoctoresServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final EspecialidadDAO espDAO = new EspecialidadDAOImpl();
    private final MedicoDAO medDAO = new MedicoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q   = opt(req.getParameter("q"));
        String esp = opt(req.getParameter("esp"));

        // Especialidades
        List<EspecialidadDTO> especialidades = espDAO.listarToda();
        Map<Integer,String> mapEsp = especialidades.stream()
                .collect(Collectors.toMap(EspecialidadDTO::getId, EspecialidadDTO::getNombre));

        // 2) Médicos 
        Integer espId = null;
        try { if (esp != null && !esp.isBlank()) espId = Integer.parseInt(esp); } catch(Exception ignore){}
        List<MedicoDTO> medicos = medDAO.listarPorEspecialidad(espId);

        // 3) Filtro por nombre/apellido 
        if (q != null && !q.isBlank()) {
            String qq = q.toLowerCase();
            medicos = medicos.stream().filter(m ->
                    (m.getNombre()+" "+m.getApellido()).toLowerCase().contains(qq)
            ).collect(Collectors.toList());
        }

        List<MedicoCardVM> vm = medicos.stream().map(m -> {
            MedicoCardVM v = new MedicoCardVM();
            v.setIdMedico(m.getIdMedico());
            v.setNombre(m.getNombre());
            v.setApellido(m.getApellido());
            v.setEspecialidad(mapEsp.getOrDefault(m.getEspecialidadId(), "—"));
            v.setHorarioDisponible(m.getHorarioDisponible());
            v.setTurno(m.getTurno());
            return v;
        }).collect(Collectors.toList());

        req.setAttribute("especialidades", especialidades);
        req.setAttribute("medicos", vm);
        req.getRequestDispatcher("/doctores.jsp").forward(req, resp);
    }

    private static String opt(String s){ return (s==null||s.isBlank())?null:s.trim(); }
}