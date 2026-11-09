<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle","Teleconsultas | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<style>
  .tc-title{ color:#4b1d7a }
  .tc-lead{ color:#5b3a86 }
  .tc-pill{ background:#fff; border:1px solid #e9e6f6 }
  .tc-img{
    border-radius:20px; box-shadow:0 14px 40px rgba(0,0,0,.08);
  }
  .tc-bullet{ color:#7623b1 }
  .btn-tc{
    background:#ff7a00; color:#fff; border:none;
    box-shadow:0 8px 24px rgba(255,122,0,.35);
  }
  .btn-tc:hover{ filter:brightness(1.05) }
  .tc-card{ border:1px solid #eee; box-shadow:0 10px 30px rgba(0,0,0,.05) }
</style>

<section class="py-4 py-md-5">
  <div class="row g-4 align-items-start">
    <div class="col-lg-7">
      <h1 class="display-6 fw-bold tc-title mb-3">Teleconsulta</h1>
      <p class="tc-lead">
        En Home Safety ponemos a tu alcance nuestro servicio de teleconsultas
        para recibir orientación médica especializada sin necesidad de desplazarte.
        Conecta de forma segura, rápida y eficiente desde tu hogar.
      </p>

      <h2 class="h4 fw-semibold mt-4 tc-title">Especialidades</h2>
      <div class="row">
        <div class="col-md-7">
          <ul class="mb-3">
            <li class="tc-bullet">Alergia e Inmunología</li>
            <li class="tc-bullet">Cardiología</li>
            <li class="tc-bullet">Cirugía general</li>
            <li class="tc-bullet">Dermatología</li>
            <li class="tc-bullet">Endocrinología</li>
            <li class="tc-bullet">Gastroenterología</li>
            <li class="tc-bullet">Geriatría</li>
            <li class="tc-bullet">Gineco Obstetricia</li>
            <li class="tc-bullet">Hematología clínica</li>
            <li class="tc-bullet">Medicina interna</li>
            <li class="tc-bullet">Nefrología</li>
            <li class="tc-bullet">Neumología</li>
            <li class="tc-bullet">Neurología</li>
            <li class="tc-bullet">Oftalmología</li>
            <li class="tc-bullet">Pediatría</li>
            <li class="tc-bullet">Psiquiatría</li>
            <li class="tc-bullet">Psicología</li>
            <li class="tc-bullet">Reumatología</li>
            <li class="tc-bullet">Urología</li>
          </ul>

          <div class="d-flex flex-wrap gap-2">
            <a class="btn btn-tc px-3" href="${pageContext.request.contextPath}/paginas/reservar.jsp">
              ¡Agenda tu teleconsulta!
            </a>
            <button class="btn btn-outline-secondary px-3" data-bs-toggle="modal" data-bs-target="#mdlContacto">
              ¡Contáctate con nosotros!
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="col-lg-5">
      <img class="img-fluid tc-img"
           src="https://images.unsplash.com/photo-1580910051074-3eb694886505?q=80&w=1200&auto=format&fit=crop"
           alt="Teleconsulta en línea">
    </div>
  </div>
</section>

<section class="mb-4">
  <div class="row g-3">
    <div class="col-md-4">
      <div class="card tc-card h-100">
        <div class="card-body">
          <span class="badge text-bg-light border mb-2 tc-pill">Paso 1</span>
          <h3 class="h6">Agenda tu cita</h3>
          <p class="small text-muted mb-0">Elige especialidad, médico y horario disponibles desde "Reservar".</p>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card tc-card h-100">
        <div class="card-body">
          <span class="badge text-bg-light border mb-2 tc-pill">Paso 2</span>
          <h3 class="h6">Conéctate</h3>
          <p class="small text-muted mb-0">Recibirás el enlace seguro en tu correo o "Mis citas". Ingresa a la hora pactada.</p>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card tc-card h-100">
        <div class="card-body">
          <span class="badge text-bg-light border mb-2 tc-pill">Paso 3</span>
          <h3 class="h6">Recibe indicaciones</h3>
          <p class="small text-muted mb-0">Tu médico registrará indicaciones y, de ser necesario, recetas o exámenes.</p>
        </div>
      </div>
    </div>
  </div>
</section>

<section class="mb-5">
  <div class="row g-3">
    <div class="col-md-6">
      <div class="card tc-card h-100">
        <div class="card-body">
          <h3 class="h6 tc-title">Requisitos mínimos</h3>
          <ul class="small text-muted mb-0">
            <li>Conexión a Internet estable (10 Mbps o más).</li>
            <li>PC/Móvil con cámara y micrófono funcionales.</li>
            <li>Navegadores recomendados: Chrome, Edge, Safari actualizados.</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <div class="card tc-card h-100">
        <div class="card-body">
          <h3 class="h6 tc-title">Recomendaciones</h3>
          <ul class="small text-muted mb-0">
            <li>Busca un lugar silencioso y con buena iluminación.</li>
            <li>Ten a la mano tu DNI e historial/recetas previas.</li>
            <li>Conéctate 5 minutos antes para probar audio y video.</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</section>

<div class="modal fade" id="mdlContacto" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Contáctate con nosotros</h5>
        <button class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form>
          <div class="row g-2">
            <div class="col-md-6">
              <label class="form-label">Nombres</label>
              <input class="form-control" placeholder="Tu nombre">
            </div>
            <div class="col-md-6">
              <label class="form-label">Correo</label>
              <input type="email" class="form-control" placeholder="tucorreo@mail.com">
            </div>
            <div class="col-12">
              <label class="form-label">Mensaje</label>
              <textarea class="form-control" rows="3" placeholder="Cuéntanos tu consulta"></textarea>
            </div>
          </div>
          <div class="form-text">* Solo diseño. Luego conectaremos con el backend.</div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
        <button class="btn btn-tc">Enviar</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="/componentes/footer.jspf" %>