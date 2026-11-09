<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
request.setAttribute("pageTitle", "Mi agenda | Doctor");
%>
<%@ include file="/componentes/header.jspf"%>

<style>
.card-soft {
	border: 1px solid rgba(0, 0, 0, .06);
	box-shadow: 0 10px 30px rgba(0, 0, 0, .05)
}

.badge-soft {
	background: #eef2ff;
	color: #4338ca
}
</style>


<c:if test="${not empty error}">
	<div class="alert alert-danger">
		<strong>Error:</strong> ${error}
	</div>
</c:if>


<%-- 
<div class="alert alert-info">
  <strong>Debug:</strong> 
  Usuario: ${sessionScope.username}, 
  Rol: ${sessionScope.rol}, 
  ID Usuario: ${sessionScope.idUsuario}, 
  ID Médico: ${sessionScope.idMedico}
</div>
--%>

<div class="row justify-content-center">
	<div class="col-xl-11">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h1 class="h5 mb-0">Mi agenda</h1>
			<form class="d-flex gap-2" method="get"
				action="${pageContext.request.contextPath}/medico/mi-agenda">
				<input type="date" class="form-control" name="fecha"
					value="${fecha}">
				<button class="btn btn-primary btn-sm">Ir</button>
			</form>
		</div>

		<!-- KPIs -->
		<div class="row g-3 mb-3">
			<div class="col-md-4">
				<div class="card card-soft p-3">
					<div class="text-muted small">Programadas hoy</div>
					<div class="fs-4">${kpis != null ? kpis.programadas : 0}</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="card card-soft p-3">
					<div class="text-muted small">Completadas</div>
					<div class="fs-4">${kpis != null ? kpis.completadas : 0}</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="card card-soft p-3">
					<div class="text-muted small">Canceladas</div>
					<div class="fs-4">${kpis != null ? kpis.canceladas : 0}</div>
				</div>
			</div>
		</div>

		<!-- Citas -->
		<div class="card card-soft">
			<div class="table-responsive">
				<table class="table align-middle mb-0">
					<thead>
						<tr>
							<th>Hora</th>
							<th>Paciente</th>
							<th>Especialidad</th>
							<th>Motivo</th>
							<th>Estado</th>
							<th class="text-end">Acciones</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty citas}">
								<tr>
									<td colspan="6" class="text-center text-muted py-4">No hay
										citas para ${fecha != null ? fecha : 'la fecha seleccionada'}.
										<c:if test="${empty kpis}">
											<br>
											<small class="text-danger">Posible error de conexión
												o configuración.</small>
										</c:if>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="cita" items="${citas}">
									<c:set var="hist" value="${historialesMap[cita.idCita]}" />
									<tr>
										<td><span class="badge badge-soft">${cita.hora}</span></td>
										<td>${cita.pacienteNombre}${cita.pacienteApellido}</td>
										<td>${cita.especialidadNombre}</td>
										<td>${cita.motivoConsulta!=null? cita.motivoConsulta : '-'}</td>
										<td><c:choose>
												<c:when test="${cita.estado=='Programada'}">
													<span class="badge bg-secondary">Programada</span>
												</c:when>
												<c:when test="${cita.estado=='Completada'}">
													<span class="badge bg-success">Completada</span>
												</c:when>
												<c:when test="${cita.estado=='Cancelada'}">
													<span class="badge bg-danger">Cancelada</span>
												</c:when>
											</c:choose></td>
										<td class="text-end">
											<!-- Cambiar estado rápido -->
											<form class="d-inline" method="post"
												action="${pageContext.request.contextPath}/medico/mi-agenda">
												<input type="hidden" name="action" value="estado"> <input
													type="hidden" name="fecha" value="${fecha}"> <input
													type="hidden" name="id_cita" value="${cita.idCita}">
												<input type="hidden" name="estado" value="Completada">
												<button class="btn btn-sm btn-success"
													${cita.estado=='Completada'?'disabled':''}>Marcar
													completada</button>
											</form>
											<form class="d-inline" method="post"
												action="${pageContext.request.contextPath}/medico/mi-agenda">
												<input type="hidden" name="action" value="estado"> <input
													type="hidden" name="fecha" value="${fecha}"> <input
													type="hidden" name="id_cita" value="${cita.idCita}">
												<input type="hidden" name="estado" value="Cancelada">
												<button class="btn btn-sm btn-outline-danger"
													${cita.estado=='Cancelada'?'disabled':''}>Cancelar</button>
											</form> <!-- Ver / Editar historial -->
											<button class="btn btn-sm btn-primary btn-hist"
												data-bs-toggle="modal" data-bs-target="#mdlHistorial"
												data-cita="${cita.idCita}"
												data-paciente="${cita.pacienteNombre} ${cita.pacienteApellido}"
												data-hist-id="${hist!=null? hist.idHistorial : ''}"
												data-hist-notas="${hist!=null? fn:escapeXml(hist.notas) : ''}">
												${hist!=null? 'Ver / Editar historial' : 'Crear historial'}
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- Modal historial -->
<div class="modal fade" id="mdlHistorial" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<form class="modal-content" method="post"
			action="${pageContext.request.contextPath}/medico/mi-agenda">
			<div class="modal-header">
				<h5 class="modal-title">Historial clínico</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<input type="hidden" name="action" value="guardar_historial">
				<input type="hidden" name="fecha" value="${fecha}"> <input
					type="hidden" name="id_cita" id="hist_id_cita"> <input
					type="hidden" name="id_historial" id="hist_id">

				<div class="mb-2 small text-muted" id="hist_paciente"></div>
				<label class="form-label">Notas</label>
				<textarea class="form-control" name="notas" id="hist_notas" rows="8"
					placeholder="Evolución, signos vitales, diagnóstico, tratamiento..."></textarea>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" type="button"
					data-bs-dismiss="modal">Cerrar</button>
				<button class="btn btn-primary" type="submit">Guardar</button>
			</div>
		</form>
	</div>
</div>

<script>
  document.querySelectorAll('.btn-hist').forEach(btn=>{
    btn.addEventListener('click', ()=>{
      document.getElementById('hist_id_cita').value = btn.dataset.cita || '';
      document.getElementById('hist_id').value = btn.dataset.histId || '';
      document.getElementById('hist_paciente').textContent = btn.dataset.paciente || '';
      document.getElementById('hist_notas').value = btn.dataset.histNotas || '';
    });
  });
</script>

<%@ include file="/componentes/footer.jspf"%>