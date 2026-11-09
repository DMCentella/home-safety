<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle","Misión y Visión | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<style>
  body{ background: linear-gradient(135deg,#f8f9fa,#eef4ff); }

  .card-soft{
    border:none; border-radius:16px; overflow:hidden;
    box-shadow:0 4px 15px rgba(0,0,0,.1);
    transition:transform .3s ease, box-shadow .3s ease;
    height:100%;
    display:flex; flex-direction:column;
  }
  .card-soft:hover{ transform:translateY(-4px); box-shadow:0 8px 25px rgba(0,0,0,.15); }

  .card-body{ position:relative; z-index:2; }
  .card-body h2{ font-weight:700; color:#0b6efd; }
  .card-body p{ font-size:.95rem; line-height:1.6; margin-bottom:0; }


  .card-media{ position:relative; width:100%; aspect-ratio:16/9; overflow:hidden; }
  .card-media img{ width:100%; height:100%; object-fit:cover; display:block; transition:transform .4s ease; }
  .card-soft:hover .card-media img{ transform:scale(1.03); }

  .line-accent{ width:50px; height:4px; background:#0b6efd; border-radius:2px; margin-bottom:1rem; }
</style>

<div class="row g-4 align-items-stretch">

  <div class="col-md-6">
    <div class="card card-soft h-100">
      <div class="card-body p-4">
        <div class="line-accent"></div>
        <h2 class="h5 mb-3">Nuestra Misión</h2>
        <p class="text-muted">
          Brindar a cada paciente acceso sencillo y seguro a servicios médicos de calidad,
          apoyándonos en la tecnología para facilitar la reserva de citas, mejorar la comunicación
          con los doctores y garantizar un seguimiento integral de la salud.
        </p>
      </div>
      <img src="img/mision.jpg" class="img-fluid" alt="Misión">
    </div>
  </div>


  <div class="col-md-6">
    <div class="card card-soft h-100">
      <div class="card-body p-4">
        <div class="line-accent"></div>
        <h2 class="h5 mb-3">Nuestra Visión</h2>
        <p class="text-muted">
          Convertirnos en la plataforma de salud más confiable y cercana, reconocida por integrar
          pacientes, doctores y consultorios en un solo ecosistema digital que promueve la
          prevención, la atención oportuna y el bienestar integral de las personas.
        </p>
      </div>
      <img src="img/vision.jpg" class="img-fluid" alt="Visión">
    </div>
  </div>
</div>

<%@ include file="/componentes/footer.jspf" %>
