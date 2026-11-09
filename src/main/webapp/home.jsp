<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Inicio | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<
<style>
:root {
	--hs-primary: #0b6efd;
	--hs-ink-2: #6b7280;
}

.hero {
	background: radial-gradient(900px 420px at -10% -20%, rgba(13, 110, 253, .15),
		transparent), linear-gradient(180deg, #eef4ff, #f8fafc);
	border: 1px solid rgba(0, 0, 0, .05);
}

.card-soft {
	transition: transform .25s ease, box-shadow .25s ease;
}

.card-soft:hover {
	transform: translateY(-4px);
	box-shadow: 0 8px 30px rgba(0, 0, 0, .08);
}

.btn-primary {
	background-image: linear-gradient(180deg, var(--hs-primary), #0a58ca);
	box-shadow: 0 6px 14px rgba(13, 110, 253, .25);
	border: none;
}

.btn-primary:hover {
	filter: brightness(1.02);
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

.accordion-button::after {
	transition: transform .2s ease;
}

.accordion-button[aria-expanded="true"]::after {
	transform: rotate(180deg);
}

.card-img-top {
	aspect-ratio: 16/9;
	width: 100%;
	object-fit: cover; /
	object-position: center;
	border-bottom: 1px solid #eee;
}
</style>


<section class="hero p-4 p-md-5 rounded-3 mb-4 reveal">
	<div class="row align-items-center">
		<div class="col-lg-7">
			<h1 class="display-6 fw-semibold mb-2">Gestiona tu salud en un
				solo lugar</h1>
			<p class="lead text-muted mb-3">Reserva citas, encuentra
				especialistas y controla tus atenciones médicas.</p>
			<div class="d-flex flex-wrap gap-2">
				<a class="btn btn-primary"
					href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar
					cita</a> <a class="btn btn-outline-primary"
					href="${pageContext.request.contextPath}/paginas/mis-citas.jsp">Ver
					mis citas</a>
			</div>
		</div>
		<div class="col-lg-5 mt-4 mt-lg-0">
			<img src="img/portada.jpg" class="img-fluid rounded"
				alt="Portada Home Safety">
		</div>
	</div>
</section>

<!-- Especialidades destacadas -->
<section class="mb-4 reveal">
	<div class="d-flex justify-content-between align-items-center mb-2">
		<h2 class="h6 mb-0">Especialidades destacadas</h2>
		<a class="btn btn-sm btn-outline-primary"
			href="${pageContext.request.contextPath}/especialidades.jsp">Ver
			todas</a>
	</div>
	<div class="row g-3">
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/especialidades/cardiologia.jpg"
					alt="Cardiología">
				<div class="card-body">
					<h5 class="card-title mb-1">Cardiología</h5>
					<p class="small text-muted mb-2">Evaluación cardiovascular
						integral.</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/especialidades/dental.jpg"
					alt="Dental">
				<div class="card-body">
					<h5 class="card-title mb-1">Dental</h5>
					<p class="small text-muted mb-2">Prevención, diagnóstico y
						tratamientos orales.</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/especialidades/psicologia.jpg"
					alt="Psicología">
				<div class="card-body">
					<h5 class="card-title mb-1">Psicología</h5>
					<p class="small text-muted mb-2">Salud mental y bienestar
						emocional.</p>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- Doctores destacados -->
<section class="mb-4 reveal">
	<div class="d-flex justify-content-between align-items-center mb-2">
		<h2 class="h6 mb-0">Doctores destacados</h2>
	</div>
	<div class="row g-3">
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/Medicos/carlosrojas.jpg"
					alt="Dr. Carlos Rojas">
				<div class="card-body">
					<h5 class="card-title mb-1">Dr. Carlos Rojas</h5>
					<p class="small text-muted mb-2">Cardiólogo</p>
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar
						cita</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/Medicos/lauratorres.png"
					alt="Dra. Laura Torres">
				<div class="card-body">
					<h5 class="card-title mb-1">Dra. Laura Torres</h5>
					<p class="small text-muted mb-2">Dentista</p>
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar
						cita</a>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<img class="card-img-top" src="img/Medicos/diegosanchez.webp"
					alt="Dr. Diego Sánchez">
				<div class="card-body">
					<h5 class="card-title mb-1">Dr. Diego Sánchez</h5>
					<p class="small text-muted mb-2">Psicólogo</p>
					<a class="btn btn-sm btn-primary"
						href="${pageContext.request.contextPath}/paginas/reservar.jsp">Reservar
						cita</a>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- Cómo funciona -->
<section class="mb-4 reveal">
	<h2 class="h6 mb-3">¿Cómo funciona?</h2>
	<div class="row g-3">
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<div class="card-body">
					<span class="badge bg-primary mb-2">1</span>
					<h3 class="h6 mb-1">Reserva tu cita</h3>
					<p class="small text-muted mb-0">Explora especialidades y
						consultorios disponibles.</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<div class="card-body">
					<span class="badge bg-primary mb-2">2</span>
					<h3 class="h6 mb-1">Selecciona al doctor</h3>
					<p class="small text-muted mb-0">Compara perfiles y elige el
						horario ideal.</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card card-soft h-100">
				<div class="card-body">
					<span class="badge bg-primary mb-2">3</span>
					<h3 class="h6 mb-1">Confirma tu cita</h3>
					<p class="small text-muted mb-0">Recibe confirmación y
						recordatorios automáticos.</p>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- FAQ -->
<section class="mb-4 reveal">
	<h2 class="h6 mb-3">Preguntas frecuentes</h2>
	<div class="accordion" id="faq">
		<div class="accordion-item">
			<h2 class="accordion-header" id="q1">
				<button class="accordion-button" type="button"
					data-bs-toggle="collapse" data-bs-target="#a1">¿Necesito
					crear una cuenta para reservar?</button>
			</h2>
			<div id="a1" class="accordion-collapse collapse show"
				data-bs-parent="#faq">
				<div class="accordion-body small text-muted">Sí, para
					gestionar tus citas y ver tu historial, necesitas una cuenta.</div>
			</div>
		</div>
		<div class="accordion-item">
			<h2 class="accordion-header" id="q2">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#a2">¿Puedo
					reprogramar o cancelar mi cita?</button>
			</h2>
			<div id="a2" class="accordion-collapse collapse"
				data-bs-parent="#faq">
				<div class="accordion-body small text-muted">Sí, desde “Mis
					Citas” podrás reprogramar o cancelar según políticas del
					consultorio.</div>
			</div>
		</div>
	</div>
</section>

<!-- ===== JS específico de la página (puedes mover a /assets/js/app.js) ===== -->
<script>
  // Reveal on scroll
  const items = document.querySelectorAll('.reveal');
  const io = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.classList.add('reveal--visible');
        io.unobserve(e.target);
      }
    });
  }, { threshold: .12 });
  items.forEach(el => io.observe(el));
</script>

<%@ include file="/componentes/footer.jspf"%>
