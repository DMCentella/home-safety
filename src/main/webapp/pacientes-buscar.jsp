<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("pageTitle","Buscar paciente | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<div class="d-flex justify-content-between align-items-center mb-3">
  <h1 class="h5 mb-0">Pacientes</h1>
 
  <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/paciente-nuevo.jsp">
    Registrar paciente
  </a>
</div>

<!-- Filtros -->
<div class="card card-soft mb-3">
  <div class="card-body">
    <form method="get" action="<c:url value='/pacientes'/>">
      <div class="row g-2 align-items-end">
        <div class="col-md-6">
          <label class="form-label">Buscar por DNI / nombre / apellido</label>
          <input name="q" class="form-control" placeholder="Ej: 12345678" value="${q}">
        </div>
        <div class="col-md-3">
          <label class="form-label">Tamaño página</label>
          <select name="size" class="form-select">
            <option ${size==10?'selected':''}>10</option>
            <option ${size==20?'selected':''}>20</option>
            <option ${size==50?'selected':''}>50</option>
          </select>
        </div>
        <div class="col-md-3 d-grid">
          <button class="btn btn-primary">Filtrar</button>
        </div>
      </div>
    </form>
  </div>
</div>

<!-- Lista -->
<div class="card card-soft">
  <div class="table-responsive">
    <table class="table align-middle mb-0">
      <thead>
        <tr>
          <th>DNI</th>
          <th>Nombre</th>
          <th>Teléfono</th>
          <th>Correo</th>
          <th>Vinculación</th>
          <th class="text-end">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty pacientes}">
            <tr><td colspan="6" class="text-center text-muted py-4">No se encontraron pacientes.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="p" items="${pacientes}">
              <tr>
                <td>${p.dni}</td>
                <td>${p.nombre} ${p.apellido}</td>
                <td>${p.telefono!=null?p.telefono:'-'}</td>
                <td>${p.email!=null?p.email:'-'}</td>
                <td>
                  <c:choose>
                    <c:when test="${not empty p.idUsuario}">
                      <span class="badge bg-success">Vinculado</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-secondary">Sin usuario</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td class="text-end">
                  <!-- Editar en modal  -->
<button type="button"
  class="btn btn-sm btn-outline-secondary btn-edit"
  data-bs-toggle="modal" data-bs-target="#mdlEditar"
  data-id="${p.idPaciente}"
  data-nombre="${p.nombre} ${p.apellido}"
  data-telefono="${p.telefono}"
  data-email="${p.email}"
  data-seguro="${p.seguroMedico}"
  data-dir="${p.direccionResidencia}"
  data-dep="${p.deptoResidencia}"
  data-prov="${p.provResidencia}"
  data-dist="${p.distResidencia}"
  data-numdep="${p.numDepartamento}"
  data-urb="${p.urbanizacion}">
  Editar
</button>


                  <!-- Reservar manual  -->
                  <c:url var="urlReservar" value="/reservar-manual">
                    <c:param name="pacienteId" value="${p.idPaciente}"/>
                  </c:url>
                  <a class="btn btn-sm btn-primary" href="${urlReservar}">Reservar manual</a>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>


  <c:if test="${pages > 1}">
    <div class="card-footer d-flex justify-content-between align-items-center">
      <span class="small text-muted">
        Mostrando ${(page-1)*size + 1}–${Math.min(page*size, total)} de ${total}
      </span>
      <nav>
        <ul class="pagination pagination-sm mb-0">
          <li class="page-item ${page==1?'disabled':''}">
            <c:url var="prevUrl" value="/pacientes"><c:param name="page" value="${page-1}"/><c:param name="size" value="${size}"/><c:param name="q" value="${q}"/></c:url>
            <a class="page-link" href="${prevUrl}">«</a>
          </li>

          <c:forEach begin="1" end="${pages}" var="pnum">
            <li class="page-item ${pnum==page?'active':''}">
              <c:url var="pageUrl" value="/pacientes"><c:param name="page" value="${pnum}"/><c:param name="size" value="${size}"/><c:param name="q" value="${q}"/></c:url>
              <a class="page-link" href="${pageUrl}">${pnum}</a>
            </li>
          </c:forEach>

          <li class="page-item ${page==pages?'disabled':''}">
            <c:url var="nextUrl" value="/pacientes"><c:param name="page" value="${page+1}"/><c:param name="size" value="${size}"/><c:param name="q" value="${q}"/></c:url>
            <a class="page-link" href="${nextUrl}">»</a>
          </li>
        </ul>
      </nav>
    </div>
  </c:if>
</div>

<!-- Modal Editar Paciente -->
<div class="modal fade" id="mdlEditar" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <form class="modal-content" method="post" action="<c:url value='/pacientes/actualizar'/>">
      <div class="modal-header">
        <h5 class="modal-title">Editar paciente</h5>
        <button class="btn-close" data-bs-dismiss="modal" type="button" aria-label="Cerrar"></button>
      </div>

      <div class="modal-body">
        <input type="hidden" name="id_paciente" id="edit_id">
        <!-- Opcional: conservar filtros/paginación al volver -->
        <input type="hidden" name="q" value="${q}">
        <input type="hidden" name="size" value="${size}">
        <input type="hidden" name="page" value="${page}">

        <div class="mb-2 small text-muted">
          <strong id="edit_titulo"></strong>
        </div>

        <!-- Contacto -->
        <div class="row g-3">
          <div class="col-md-6">
            <label class="form-label">Teléfono</label>
            <input name="telefono" id="edit_tel" class="form-control" maxlength="15">
          </div>
          <div class="col-md-6">
            <label class="form-label">Correo</label>
            <input type="email" name="email" id="edit_email" class="form-control" maxlength="100">
          </div>
          <div class="col-md-6">
            <label class="form-label">Seguro médico</label>
            <input name="seguro_medico" id="edit_seguro" class="form-control" maxlength="100">
          </div>
        </div>

        <hr class="my-3">

        <!-- Residencia -->
        <h6 class="mb-2">Residencia</h6>
        <div class="row g-3">
          <div class="col-12">
            <label class="form-label">Dirección</label>
            <input name="direccion_residencia" id="edit_dir" class="form-control" maxlength="200">
          </div>
          <div class="col-md-4">
            <label class="form-label">Departamento</label>
            <input name="depto_residencia" id="edit_dep" class="form-control" maxlength="50">
          </div>
          <div class="col-md-4">
            <label class="form-label">Provincia</label>
            <input name="prov_residencia" id="edit_prov" class="form-control" maxlength="50">
          </div>
          <div class="col-md-4">
            <label class="form-label">Distrito</label>
            <input name="dist_residencia" id="edit_dist" class="form-control" maxlength="50">
          </div>
          <div class="col-md-4">
            <label class="form-label">N° Departamento</label>
            <input name="num_departamento" id="edit_numdep" class="form-control" maxlength="10">
          </div>
          <div class="col-md-8">
            <label class="form-label">Urbanización</label>
            <input name="urbanizacion" id="edit_urb" class="form-control" maxlength="100">
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-outline-secondary" type="button" data-bs-dismiss="modal">Cancelar</button>
        <button class="btn btn-primary" type="submit">Guardar cambios</button>
      </div>
    </form>
  </div>
</div>

<script>
  // Rellena el modal con los datos del paciente seleccionado
  document.querySelectorAll('.btn-edit').forEach(btn => {
    btn.addEventListener('click', () => {
      // ids del modal
      const $ = id => document.getElementById(id);

      $('edit_id').value       = btn.dataset.id || '';
      $('edit_titulo').textContent = btn.dataset.nombre || '';

      $('edit_tel').value      = btn.dataset.telefono || '';
      $('edit_email').value    = btn.dataset.email || '';
      $('edit_seguro').value   = btn.dataset.seguro || '';

      $('edit_dir').value      = btn.dataset.dir || '';
      $('edit_dep').value      = btn.dataset.dep || '';
      $('edit_prov').value     = btn.dataset.prov || '';
      $('edit_dist').value     = btn.dataset.dist || '';
      $('edit_numdep').value   = btn.dataset.numdep || '';
      $('edit_urb').value      = btn.dataset.urb || '';
    });
  });
</script>


<%@ include file="/componentes/footer.jspf" %>

