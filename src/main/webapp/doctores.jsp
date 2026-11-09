<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
request.setAttribute("pageTitle", "Doctores | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<style>
:root {
	--hs-primary: #0b6efd;
}

.doc-hero {
	background: radial-gradient(900px 420px at -10% -20%, rgba(13, 110, 253, .10),
		transparent), linear-gradient(180deg, #eef4ff, #f8fafc);
	border: 1px solid rgba(0, 0, 0, .05);
}

.doc-card {
	border: 1px solid rgba(0, 0, 0, .06);
	box-shadow: 0 10px 30px rgba(0, 0, 0, .05);
	transition: transform .25s ease, box-shadow .25s ease, border-color .25s
		ease;
}

.doc-card:hover {
	transform: translateY(-4px);
	box-shadow: 0 16px 46px rgba(0, 0, 0, .10);
	border-color: rgba(13, 110, 253, .25);
}

.doc-card .card-img-top {
	height: 180px;
	object-fit: cover;
}

.reveal {
	opacity: 0;
	transform: translateY(14px);
}

.reveal.reveal--visible {
	opacity: 1;
	transform: none;
	transition: opacity .6s ease, transform .6s ease;
}
</style>

<!-- ===== Encabezado ===== -->
<section class="doc-hero p-4 p-md-5 rounded-3 mb-4 reveal">
	<h1 class="h4 mb-1">Nuestros Doctores</h1>
	<p class="text-muted mb-0">Conoce a los especialistas que forman
		parte de Home Safety.</p>
</section>


<div class="card card-soft mb-3 reveal">
	<div class="card-body">
		<form class="row g-2 align-items-end" method="get"
			action="${pageContext.request.contextPath}/doctores">
			<div class="col-md-5">
				<label class="form-label">Buscar por nombre</label> <input
					type="text" class="form-control" name="q" value="${param.q}"
					placeholder="Ej: Juan Pérez">
			</div>
			<div class="col-md-5">
				<label class="form-label">Especialidad</label> <select
					class="form-select" name="esp">
					<option value="">Todas</option>
					<c:forEach var="e" items="${especialidades}">
						<option value="${e.id}" ${param.esp == e.id ? 'selected' : ''}>${e.nombre}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-2 d-grid d-md-block">
				<button class="btn btn-primary">Buscar</button>
				<a class="btn btn-outline-secondary ms-md-2 mt-2 mt-md-0"
					href="${pageContext.request.contextPath}/doctores">Limpiar</a>
			</div>
		</form>
	</div>
</div>

<!-- ===== Grid de doctores ===== -->
<div class="row g-3 reveal">
	<c:forEach var="m" items="${medicos}">
		<div class="col-md-4">
			<div class="card doc-card h-100">
				<img
					onerror="this.src='${pageContext.request.contextPath}/img/medicos/vacio.png'"
					alt="${m.nombre} ${m.apellido}"
					src="${pageContext.request.contextPath}/img/medicos/${m.idMedico}.png"
					class="card-img-top">

				<div class="card-body">
					<h5 class="card-title mb-1">${m.nombre}${m.apellido}</h5>
					<p class="small text-muted mb-1">${m.especialidad}</p>
					<p class="small text-muted mb-2">
						<strong>Horario:</strong>
						<c:out
							value="${empty m.horarioDisponible ? '—' : m.horarioDisponible}" />
						<c:if test="${not empty m.turno}"> · <span
								class="text-secondary">Turno ${m.turno}</span>
						</c:if>
					</p>
					<div class="d-flex gap-2 mt-2">
						<a class="btn btn-sm btn-primary"
							href="${pageContext.request.contextPath}/reservar?idMedico=${m.idMedico}">Reservar
							cita</a> <a class="btn btn-sm btn-outline-primary" href="#">Ver
							perfil</a>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>

	<c:if test="${empty medicos}">
		<div class="col-12">
			<div class="alert alert-light border text-muted">No se
				encontraron doctores con los criterios seleccionados.</div>
		</div>
	</c:if>
</div>


<script>
  (function(){
    const items = document.querySelectorAll('.reveal');
    const io = new IntersectionObserver((entries)=>{
      entries.forEach(e=>{
        if(e.isIntersecting){ e.target.classList.add('reveal--visible'); io.unobserve(e.target); }
      });
    }, {threshold:.12});
    items.forEach(el=>io.observe(el));
  })();
</script>

<%@ include file="/componentes/footer.jspf"%>