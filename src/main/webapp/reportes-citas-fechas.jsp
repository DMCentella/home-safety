<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle","Reporte: Citas por fechas | Home Safety"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/componentes/header.jspf" %>

<style>
  .card-soft{border:1px solid rgba(0,0,0,.06); box-shadow:0 10px 30px rgba(0,0,0,.05); border-radius:.75rem}
  .table thead th{ background:#f8fafc; border-bottom-color:#e5e7eb; font-weight:600 }
  .table tbody tr:hover{ background:#f8fbff }
  .badge{ border-radius:999px; padding:.35rem .6rem; font-weight:600 }
  .b-prog{ background:#0d6efd1a; color:#0b5ed7; border:1px solid #0d6efd33 }
  .b-comp{ background:#1987541a; color:#157347; border:1px solid #19875433 }
  .b-canc{ background:#dc35451a; color:#b02a37; border:1px solid #dc354533 }
</style>

<main class="container my-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h5 mb-0">Reporte: Citas por fechas</h1>
    <div class="d-flex gap-2">
      <button class="btn btn-outline-secondary btn-sm" onclick="window.print()">Imprimir</button>
      <a class="btn btn-outline-primary btn-sm"
         href="${pageContext.request.contextPath}/reportes/citas-fechas.csv?desde=${desde}&hasta=${hasta}&estado=${estado}&especialidadId=${especialidadId}">
        Exportar CSV
      </a>
    </div>
  </div>

  <div class="card card-soft mb-3">
    <div class="card-body">
      <form class="row g-2 align-items-end" method="get" action="${pageContext.request.contextPath}/reportes/citas-fechas">
        <div class="col-md-3">
          <label class="form-label">Desde</label>
          <input type="date" class="form-control" name="desde" value="${desde}">
        </div>
        <div class="col-md-3">
          <label class="form-label">Hasta</label>
          <input type="date" class="form-control" name="hasta" value="${hasta}">
        </div>
        <div class="col-md-3">
          <label class="form-label">Especialidad</label>
          <select class="form-select" name="especialidadId">
            <option value="">Todas</option>
            <c:forEach var="e" items="${especialidades}">
              <option value="${e.id}" ${especialidadId==e.id ? 'selected' : ''}>${e.nombre}</option>
            </c:forEach>
          </select>
        </div>
        <div class="col-md-3">
          <label class="form-label">Estado</label>
          <select class="form-select" name="estado">
            <option value="">Todos</option>
            <option value="Programada" ${estado=='Programada'?'selected':''}>Programada</option>
            <option value="Completada" ${estado=='Completada'?'selected':''}>Completada</option>
            <option value="Cancelada"  ${estado=='Cancelada' ?'selected':''}>Cancelada</option>
          </select>
        </div>
        <div class="col-12 d-flex gap-2 mt-2">
          <button class="btn btn-primary">Consultar</button>
          <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/reportes/citas-fechas">Limpiar</a>
        </div>
      </form>
    </div>
  </div>

  <div class="row g-3 mb-3">
    <div class="col-6 col-md-3">
      <div class="card card-soft h-100"><div class="card-body">
        <div class="small text-muted">Total</div>
        <div class="fs-4 fw-semibold">${kpis.total!=null?kpis.total:0}</div>
      </div></div>
    </div>
    <div class="col-6 col-md-3">
      <div class="card card-soft h-100"><div class="card-body">
        <div class="small text-muted">Completadas</div>
        <div class="fs-4 fw-semibold text-success">${kpis.completadas!=null?kpis.completadas:0}</div>
      </div></div>
    </div>
    <div class="col-6 col-md-3">
      <div class="card card-soft h-100"><div class="card-body">
        <div class="small text-muted">Programadas</div>
        <div class="fs-4 fw-semibold">${kpis.programadas!=null?kpis.programadas:0}</div>
      </div></div>
    </div>
    <div class="col-6 col-md-3">
      <div class="card card-soft h-100"><div class="card-body">
        <div class="small text-muted">Canceladas</div>
        <div class="fs-4 fw-semibold text-danger">${kpis.canceladas!=null?kpis.canceladas:0}</div>
      </div></div>
    </div>
  </div>

  <div class="card card-soft">
    <div class="table-responsive">
      <table class="table align-middle mb-0">
        <thead>
          <tr>
            <th>Fecha</th><th>Hora</th><th>Paciente</th><th>Doctor</th><th>Especialidad</th><th>Estado</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="r" items="${filas}">
            <tr>
              <td>${r.fecha}</td>
              <td>${r.hora}</td>
              <td>${r.paciente}</td>
              <td>${r.doctor}</td>
              <td>${r.especialidad}</td>
              <td>
                <c:choose>
                  <c:when test="${r.estado=='Completada'}"><span class="badge b-comp">Completada</span></c:when>
                  <c:when test="${r.estado=='Cancelada'}"><span class="badge b-canc">Cancelada</span></c:when>
                  <c:otherwise><span class="badge b-prog">Programada</span></c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>

          <c:if test="${empty filas}">
            <tr><td colspan="6" class="text-center text-muted py-4">No hay resultados para los filtros seleccionados.</td></tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <c:if test="${pages > 1}">
      <div class="card-footer d-flex justify-content-between align-items-center">
        <span class="small text-muted">Mostrando página ${page} de ${pages} — Total: ${count}</span>
        <ul class="pagination pagination-sm mb-0">
          <li class="page-item ${page==1?'disabled':''}">
            <a class="page-link" href="${pageContext.request.contextPath}/reportes/citas-fechas?desde=${desde}&hasta=${hasta}&estado=${estado}&especialidadId=${especialidadId}&page=${page-1}">«</a>
          </li>
          <c:forEach var="p" begin="1" end="${pages}">
            <li class="page-item ${p==page?'active':''}">
              <a class="page-link" href="${pageContext.request.contextPath}/reportes/citas-fechas?desde=${desde}&hasta=${hasta}&estado=${estado}&especialidadId=${especialidadId}&page=${p}">${p}</a>
            </li>
          </c:forEach>
          <li class="page-item ${page==pages?'disabled':''}">
            <a class="page-link" href="${pageContext.request.contextPath}/reportes/citas-fechas?desde=${desde}&hasta=${hasta}&estado=${estado}&especialidadId=${especialidadId}&page=${page+1}">»</a>
          </li>
        </ul>
      </div>
    </c:if>
  </div>
</main>

<%@ include file="/componentes/footer.jspf" %>