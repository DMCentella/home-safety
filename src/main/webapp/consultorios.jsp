<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Consultorios | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<div class="card card-soft mb-3">
	<div class="card-body">
		<div class="row g-2 align-items-end">
			<div class="col-md-4">
				<label class="form-label">Buscar por distrito / sede</label> <input
					class="form-control"
					placeholder="Ej: Miraflores, San Isidro, Sede Norte">
			</div>
			<div class="col-md-3">
				<label class="form-label">Ciudad</label> <select class="form-select">
					<option value="">Todas</option>
					<option>Lima</option>
					<option>Arequipa</option>
					<option>Trujillo</option>
				</select>
			</div>
			<div class="col-md-3">
				<label class="form-label">Servicios</label> <select
					class="form-select">
					<option>Todos</option>
					<option>Consulta</option>
					<option>Emergencia</option>
					<option>Diagnóstico</option>
					<option>Rehabilitación</option>
				</select>
			</div>
			<div class="col-md-2 d-grid">
				<button class="btn btn-primary">Filtrar</button>
			</div>
		</div>
	</div>
</div>

<div class="row g-3">

	<div class="col-md-6">
		<div class="card card-soft h-100">
			<img src="https://picsum.photos/seed/sede-central/900/300"
				class="card-img-top" alt="Sede Central">
			<div class="card-body">
				<h5 class="card-title mb-1">Sede Central</h5>
				<p class="text-muted small mb-2">Av. Salud 123, Cercado – Lima</p>
				<ul class="small text-muted mb-3">
					<li>Horarios: Lun–Sáb 8:00–20:00</li>
					<li>Servicios: Consulta, Diagnóstico, Rehabilitación</li>
					<li>Tel: (01) 555-1111</li>
				</ul>
				<div class="ratio ratio-16x9 rounded border">
					<iframe
						src="https://maps.google.com/maps?q=Av.%20Salud%20123%20Lima&t=&z=14&ie=UTF8&iwloc=&output=embed"
						loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
				</div>
				<div class="d-flex gap-2 mt-3">
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar</a>
					<a class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/paginas/especialidades.jsp">Ver
						especialidades</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Sede Norte -->
	<div class="col-md-6">
		<div class="card card-soft h-100">
			<img src="https://picsum.photos/seed/sede-norte/900/300"
				class="card-img-top" alt="Sede Norte">
			<div class="card-body">
				<h5 class="card-title mb-1">Sede Norte</h5>
				<p class="text-muted small mb-2">Jr. Bienestar 456, Los Olivos –
					Lima</p>
				<ul class="small text-muted mb-3">
					<li>Horarios: Lun–Sáb 9:00–19:00</li>
					<li>Servicios: Consulta, Emergencia</li>
					<li>Tel: (01) 555-2222</li>
				</ul>
				<div class="ratio ratio-16x9 rounded border">
					<iframe
						src="https://maps.google.com/maps?q=Los%20Olivos%20Lima&t=&z=13&ie=UTF8&iwloc=&output=embed"
						loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
				</div>
				<div class="d-flex gap-2 mt-3">
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar</a>
					<a class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/paginas/especialidades.jsp">Ver
						especialidades</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Sede Sur -->
	<div class="col-md-6">
		<div class="card card-soft h-100">
			<img src="https://picsum.photos/seed/sede-sur/900/300"
				class="card-img-top" alt="Sede Sur">
			<div class="card-body">
				<h5 class="card-title mb-1">Sede Sur</h5>
				<p class="text-muted small mb-2">Av. Primavera 987, Surco – Lima</p>
				<ul class="small text-muted mb-3">
					<li>Horarios: Lun–Dom 7:00–21:00</li>
					<li>Servicios: Consulta, Diagnóstico, Emergencia</li>
					<li>Tel: (01) 555-3333</li>
				</ul>
				<div class="ratio ratio-16x9 rounded border">
					<iframe
						src="https://maps.google.com/maps?q=Surco%20Lima&t=&z=13&ie=UTF8&iwloc=&output=embed"
						loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
				</div>
				<div class="d-flex gap-2 mt-3">
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar</a>
					<a class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/paginas/especialidades.jsp">Ver
						especialidades</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Sede Arequipa -->
	<div class="col-md-6">
		<div class="card card-soft h-100">
			<img src="https://picsum.photos/seed/sede-arequipa/900/300"
				class="card-img-top" alt="Sede Arequipa">
			<div class="card-body">
				<h5 class="card-title mb-1">Sede Arequipa</h5>
				<p class="text-muted small mb-2">Calle Salud 220, Cercado –
					Arequipa</p>
				<ul class="small text-muted mb-3">
					<li>Horarios: Lun–Sáb 8:00–18:00</li>
					<li>Servicios: Consulta, Rehabilitación</li>
					<li>Tel: (054) 555-444</li>
				</ul>
				<div class="ratio ratio-16x9 rounded border">
					<iframe
						src="https://maps.google.com/maps?q=Arequipa&t=&z=12&ie=UTF8&iwloc=&output=embed"
						loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
				</div>
				<div class="d-flex gap-2 mt-3">
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar</a>
					<a class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/paginas/especialidades.jsp">Ver
						especialidades</a>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="d-flex justify-content-center mt-4">
	<a class="btn btn-lg btn-primary"
		href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar
		una cita</a>
</div>

<%@ include file="/componentes/footer.jspf"%>
