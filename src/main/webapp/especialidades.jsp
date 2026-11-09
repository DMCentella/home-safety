<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Especialidades | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<!-- ===== Estilos de la página ===== -->
<style>
:root {
	--hs-primary: #6b2ebd; /* morado suave */
	--hs-ink-2: #6b7280;
}

.sp-hero {
	background: radial-gradient(900px 420px at -10% -20%, rgba(107, 46, 189, .10),
		transparent), linear-gradient(180deg, #f5f1ff, #f8fafc);
	border: 1px solid rgba(0, 0, 0, .05);
}

.sp-card {
	border: 1px solid rgba(0, 0, 0, .06);
	box-shadow: 0 10px 30px rgba(0, 0, 0, .05);
	transition: transform .25s ease, box-shadow .25s ease, border-color .25s
		ease;
}

.sp-card:hover {
	transform: translateY(-4px);
	box-shadow: 0 16px 46px rgba(0, 0, 0, .10);
	border-color: rgba(107, 46, 189, .25);
}

.sp-card .card-img-top {
	height: 180px;
	object-fit: cover;
}

.btn-outline-primary {
	--bs-btn-color: var(--hs-primary);
	--bs-btn-border-color: var(--hs-primary);
	--bs-btn-hover-bg: var(--hs-primary);
	--bs-btn-hover-border-color: var(--hs-primary);
}

/* Animación al hacer scroll */
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

<!-- Encabezado -->
<section class="sp-hero p-4 p-md-5 rounded-3 mb-4 reveal">
	<div class="row align-items-center">
		<div class="col-lg-8">
			<h1 class="display-6 fw-semibold mb-2" style="color: #4b1d7a">Especialidades</h1>
			<p class="lead text-muted mb-0">Encuentra la especialidad que
				necesitas y revisa los perfiles de nuestros profesionales.</p>
		</div>
	</div>
</section>

<!-- Grilla de especialidades -->
<div class="row g-3 reveal">
	<!-- Radiología -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/radiologia.png" class="card-img-top"
				alt="Radiología">
			<div class="card-body">
				<h5 class="card-title mb-1">Radiología</h5>
				<p class="small text-muted mb-3">Diagnóstico por imágenes (RX,
					US, TC, RM).</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Radiología">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Otorrinolaringología -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/otorrinolaringologia.jpg"
				class="card-img-top" alt="Otorrinolaringología">
			<div class="card-body">
				<h5 class="card-title mb-1">Otorrinolaringología</h5>
				<p class="small text-muted mb-3">Oído, nariz y garganta.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Otorrinolaringología">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Oftalmología -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/oftalmologia.webp" class="card-img-top"
				alt="Oftalmología">
			<div class="card-body">
				<h5 class="card-title mb-1">Oftalmología</h5>
				<p class="small text-muted mb-3">Salud visual integral.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Oftalmología">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Dental -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/dental.jpg" class="card-img-top"
				alt="Dental">
			<div class="card-body">
				<h5 class="card-title mb-1">Dental</h5>
				<p class="small text-muted mb-3">Prevención, diagnóstico y
					tratamientos orales.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Dental">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Audiometría -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/audiometria.webp" class="card-img-top"
				alt="Audiometría">
			<div class="card-body">
				<h5 class="card-title mb-1">Audiometría</h5>
				<p class="small text-muted mb-3">Evaluaciones auditivas
					especializadas.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Audiometría">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Cardiología -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/cardiologia.jpg" class="card-img-top"
				alt="Cardiología">
			<div class="card-body">
				<h5 class="card-title mb-1">Cardiología</h5>
				<p class="small text-muted mb-3">Prevención y tratamiento
					cardiovascular.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Cardiología">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Espirometría -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/espirometria.jpg" class="card-img-top"
				alt="Espirometría">
			<div class="card-body">
				<h5 class="card-title mb-1">Espirometría</h5>
				<p class="small text-muted mb-3">Evaluación de la función
					pulmonar.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Espirometría">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Medicina Interna -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/Medicina-Interna.jpg"
				class="card-img-top" alt="Medicina Interna">
			<div class="card-body">
				<h5 class="card-title mb-1">Medicina Interna</h5>
				<p class="small text-muted mb-3">Atención integral del adulto.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Medicina%20Interna">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Psicología -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/psicologia.jpg" class="card-img-top"
				alt="Psicología">
			<div class="card-body">
				<h5 class="card-title mb-1">Psicología</h5>
				<p class="small text-muted mb-3">Salud mental y bienestar
					emocional.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Psicología">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Laboratorio Clínico -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/laboratorioclinico.webp"
				class="card-img-top" alt="Laboratorio Clínico">
			<div class="card-body">
				<h5 class="card-title mb-1">Laboratorio Clínico</h5>
				<p class="small text-muted mb-3">Análisis clínicos y pruebas
					especializadas.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Laboratorio%20Clínico">Ver
					doctores</a>
			</div>
		</div>
	</div>

	<!-- Psicosométrico -->
	<div class="col-sm-6 col-lg-4">
		<div class="card sp-card h-100">
			<img src="img/especialidades/psicometrico.jpg" class="card-img-top"
				alt="Psicosométrico">
			<div class="card-body">
				<h5 class="card-title mb-1">Psicosométrico</h5>
				<p class="small text-muted mb-3">Evaluación psicológica laboral.</p>
				<a class="btn btn-sm btn-outline-primary"
					href="${pageContext.request.contextPath}/doctores.jsp?esp=Psicosométrico">Ver
					doctores</a>
			</div>
		</div>
	</div>
</div>

<!-- ===== JS de animación ===== -->
<script>
  // Reveal on scroll (sin librerías)
  (function(){
    const items = document.querySelectorAll('.reveal');
    const io = new IntersectionObserver((entries)=>{
      entries.forEach(e=>{
        if(e.isIntersecting){
          e.target.classList.add('reveal--visible');
          io.unobserve(e.target);
        }
      });
    }, {threshold:.12});
    items.forEach(el=>io.observe(el));
  })();
</script>

<%@ include file="/componentes/footer.jspf"%>
