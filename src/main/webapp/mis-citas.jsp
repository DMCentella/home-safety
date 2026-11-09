<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setAttribute("pageTitle","Mis Citas | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<style>
  .card-soft{border:1px solid rgba(0,0,0,.06); box-shadow:0 10px 30px rgba(0,0,0,.05)}
  .badge-estado {
    font-size: 0.75rem;
    padding: 0.4rem 0.8rem;
    border-radius: 12px;
  }
  .cita-card {
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }
  .cita-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0,0,0,.1);
  }
</style>

<div class="row justify-content-center">
  <div class="col-lg-10">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="h5 mb-0">Mis Citas</h1>
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/reservar">Nueva cita</a>
    </div>

  
    <c:if test="${not empty param.creada}">
      <div class="alert alert-success py-2 mb-3">
        ✅ ¡Cita creada exitosamente! ID: ${param.creada}
        <br><small>Tu cita ha sido programada y aparece en la lista a continuación.</small>
      </div>
    </c:if>


    <c:if test="${not empty error}">
      <div class="alert alert-danger py-2 mb-3">${error}</div>
    </c:if>

 
    <c:if test="${sinPerfil}">
      <div class="card card-soft">
        <div class="card-body text-center py-4">
          <h6 class="text-muted">No tienes un perfil de paciente</h6>
          <p class="text-muted mb-3">Para ver tus citas, primero debes completar tu registro como paciente.</p>
          <a class="btn btn-primary" href="${pageContext.request.contextPath}/paciente-registrar.jsp">Registrarme como paciente</a>
        </div>
      </div>
    </c:if>

    <!-- Lista de citas (sin filtros) -->
    <c:if test="${not sinPerfil}">
      <div class="card card-soft">
        <div class="card-body">
          <c:choose>
            <c:when test="${empty citas}">
              <div class="text-center py-4">
                <p class="text-muted mb-3">No tienes citas programadas.</p>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/reservar">Reservar mi primera cita</a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="row g-3">
                <c:forEach var="cita" items="${citas}">
                  <div class="col-md-6">
                    <div class="card cita-card h-100">
                      <div class="card-body">
                        <!-- Estado y fecha -->
                        <div class="d-flex justify-content-between align-items-start mb-2">
                          <c:choose>
                            <c:when test="${cita.estado == 'Programada'}">
                              <span class="badge badge-estado bg-primary text-white">Programada</span>
                            </c:when>
                            <c:when test="${cita.estado == 'Completada'}">
                              <span class="badge badge-estado bg-success text-white">Completada</span>
                            </c:when>
                            <c:when test="${cita.estado == 'Cancelada'}">
                              <span class="badge badge-estado bg-danger text-white">Cancelada</span>
                            </c:when>
                          </c:choose>
                          <small class="text-muted">ID: ${cita.idCita}</small>
                        </div>

                        <!-- Información del médico -->
                        <h6 class="card-title mb-1">
                          <c:choose>
                            <c:when test="${not empty cita.medicoNombre}">
                              Dr. ${cita.medicoNombre} ${cita.medicoApellido}
                            </c:when>
                            <c:otherwise>
                              Médico ID: ${cita.idMedico}
                            </c:otherwise>
                          </c:choose>
                        </h6>
                        
                        <c:if test="${not empty cita.especialidadNombre}">
                          <p class="text-muted small mb-2">${cita.especialidadNombre}</p>
                        </c:if>

                        <!-- Fecha y hora -->
                        <div class="d-flex align-items-center mb-2">
                          <i class="text-primary me-2">📅</i>
                          <span>
                            <fmt:formatDate value="${cita.fechaHoraDate}" pattern="dd/MM/yyyy" />
                            a las 
                            <fmt:formatDate value="${cita.fechaHoraDate}" pattern="HH:mm" />
                          </span>
                        </div>

                        <!-- Motivo -->
                        <c:if test="${not empty cita.motivoConsulta}">
                          <div class="d-flex align-items-start mb-2">
                            <i class="text-secondary me-2">💬</i>
                            <small class="text-muted">${cita.motivoConsulta}</small>
                          </div>
                        </c:if>

                        <!-- Diagnóstico y tratamiento (si existen) -->
                        <c:if test="${not empty cita.diagnostico}">
                          <div class="mt-2 p-2 bg-light rounded">
                            <strong class="small">Diagnóstico:</strong>
                            <p class="small mb-1">${cita.diagnostico}</p>
                            <c:if test="${not empty cita.tratamiento}">
                              <strong class="small">Tratamiento:</strong>
                              <p class="small mb-0">${cita.tratamiento}</p>
                            </c:if>
                          </div>
                        </c:if>

                       
<div class="d-flex gap-2 mt-3">
  <c:if test="${cita.estado == 'Programada'}">
    <!-- Reprogramar: abre modal -->
    <button type="button"
            class="btn btn-sm btn-outline-primary btn-reprogramar"
            data-id="${cita.idCita}"
            data-idmedico="${cita.idMedico}">
      Reprogramar
    </button>

    <!-- Cancelar-->
    <form method="post" action="<c:url value='/citas/cancelar'/>" class="d-inline">
      <input type="hidden" name="id_cita" value="${cita.idCita}">
      <button class="btn btn-sm btn-outline-danger" type="submit"
              onclick="return confirm('¿Cancelar esta cita?');">
        Cancelar
      </button>
    </form>
  </c:if>
</div>

                      </div>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </c:if>
  </div>
  <!-- Modal Reprogramar -->
<div class="modal fade" id="modalReprogramar" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content" method="post" action="<c:url value='/citas/reprogramar'/>" id="frmReprogramar">
      <div class="modal-header">
        <h5 class="modal-title">Reprogramar cita</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="id_cita" id="rpIdCita">
        <input type="hidden" name="id_medico" id="rpIdMedico">
        <div class="mb-3">
          <label class="form-label">Nueva fecha y hora</label>
          <input type="datetime-local" class="form-control" name="nueva_fecha_hora" id="rpFechaHora" required>
          <div class="form-text">Debe ser una fecha futura y libre de choques.</div>
        </div>
        <div class="mb-2">
          <label class="form-label">Motivo (opcional)</label>
          <input type="text" class="form-control" name="motivo" maxlength="255" placeholder="Opcional">
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
        <button type="submit" class="btn btn-primary">Guardar cambios</button>
      </div>
    </form>
  </div>
</div>
  
</div>
<script>
  // Bootstrap modal (asumiendo que ya incluyes Bootstrap JS)
  const modalEl = document.getElementById('modalReprogramar');
  const rpIdCita   = document.getElementById('rpIdCita');
  const rpIdMedico = document.getElementById('rpIdMedico');
  const rpFecha    = document.getElementById('rpFechaHora');

  // Min para datetime-local (no permitir pasado)
  (function setMinNow(){
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    rpFecha.min = now.toISOString().slice(0,16);
  })();

  // Abrir modal con datos
  document.querySelectorAll('.btn-reprogramar').forEach(btn=>{
    btn.addEventListener('click', ()=>{
      rpIdCita.value   = btn.dataset.id;
      rpIdMedico.value = btn.dataset.idmedico;
      rpFecha.value    = ''; // el usuario elige nueva fecha/hora
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    });
  });

  // Validación básica antes de enviar
  document.getElementById('frmReprogramar')?.addEventListener('submit', (e)=>{
    if(!rpFecha.value){
      e.preventDefault();
      alert('Seleccione la nueva fecha y hora');
      return;
    }
    const dt = new Date(rpFecha.value);
    if(dt < new Date()){
      e.preventDefault();
      alert('La nueva fecha/hora debe ser futura');
      return;
    }
  });
</script>

<%@ include file="/componentes/footer.jspf" %>