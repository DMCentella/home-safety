<%-- src/main/webapp/admin/gestiones.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle","Gestiones | Admin"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/componentes/header.jspf" %>

<div class="d-flex justify-content-between align-items-center mb-3">
  <h1 class="h5 mb-0">Gestión de usuarios, roles y vínculos</h1>
  <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/home.jsp">Volver</a>
</div>

<c:if test="${not empty param.error}">
  <div class="alert alert-danger py-2">${param.error}</div>
</c:if>
<c:if test="${not empty param.msg}">
  <div class="alert alert-success py-2">${param.msg}</div>
</c:if>

<div class="card card-soft">
  <div class="table-responsive">
    <table class="table align-middle mb-0">
      <thead>
        <tr>
          <th>Usuario</th>
          <th>Email</th>
          <th>Roles</th>
          <th>Vínculo Médico</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${usuarios}">
        <tr>
          <td>
            <div class="fw-semibold">${u.username}</div>
            <div class="text-muted small">ID: ${u.idUsuario}</div>
          </td>
          <td>${u.email!=null?u.email:'-'}</td>

          <td>
            <form method="post" class="d-flex align-items-center gap-2"
                  action="${pageContext.request.contextPath}/admin/gestiones/roles">
              <input type="hidden" name="id_usuario" value="${u.idUsuario}"/>

              <label class="form-check me-2">
                <input class="form-check-input" type="checkbox" name="roles" value="ADMIN"
       ${u.hasRole('ADMIN') ? 'checked' : ''} />
<span class="form-check-label">ADMIN</span>
              </label>

              <label class="form-check me-2">
              <input class="form-check-input" type="checkbox" name="roles" value="RECEPCIONISTA"
       ${u.hasRole('RECEPCIONISTA') ? 'checked' : ''} />
<span class="form-check-label">RECEPCIONISTA</span>
              </label>

              <label class="form-check me-2">
                <input class="form-check-input" type="checkbox" name="roles" value="MEDICO"
       ${u.hasRole('MEDICO') ? 'checked' : ''} />
<span class="form-check-label">MEDICO</span>
              </label>

              <label class="form-check me-2">
<input class="form-check-input" type="checkbox" name="roles" value="PACIENTE"
       ${u.hasRole('PACIENTE') ? 'checked' : ''} />
<span class="form-check-label">PACIENTE</span>
              </label>

              <button class="btn btn-sm btn-primary">Guardar</button>
            </form>
          </td>

          <td>
            <form method="post" class="d-flex align-items-center gap-2"
                  action="${pageContext.request.contextPath}/admin/gestiones/vincular-medico">
              <input type="hidden" name="id_usuario" value="${u.idUsuario}"/>

              <select name="id_medico" class="form-select form-select-sm" style="min-width:260px">
                <option value="">— Sin vínculo —</option>
                <c:forEach var="m" items="${medicos}">
                 <option value="${m.idMedico}"
  ${u.idMedico != null && u.idMedico == m.idMedico ? 'selected' : ''}>
  ${m.apellido}, ${m.nombre} — Turno ${m.turno} (${m.idUsuario!=null?'VINCULADO':'LIBRE'})
</option>

                </c:forEach>
              </select>

              <button class="btn btn-sm btn-outline-primary">Actualizar</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<%@ include file="/componentes/footer.jspf" %>
