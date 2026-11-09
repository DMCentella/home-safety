<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("pageTitle","Reservar Cita | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<style>
  .card-soft{border:1px solid rgba(0,0,0,.06); box-shadow:0 10px 30px rgba(0,0,0,.05)}
  .slot{padding:.45rem .7rem; border:1px solid #d0d7e2; border-radius:.5rem; background:#fff; cursor:pointer}
  .slot:hover{background:#f5f9ff}
  .slot.active{background:#0b6efd; color:#fff; border-color:#0b6efd}
  .muted{color:#6b7280}
</style>

<div class="row justify-content-center">
  <div class="col-xl-10">
    <div class="card card-soft">
      <div class="card-body">
        <h1 class="h5 mb-3">Reservar cita</h1>

   
        <c:if test="${not empty error}">
          <div class="alert alert-danger py-2">${error}</div>
        </c:if>
        <c:if test="${not empty param.msg}">
          <div class="alert alert-success py-2">${param.msg}</div>
        </c:if>

        <!-- Formulario de reserva -->
        <form method="post" action="${pageContext.request.contextPath}/citas/crear" id="frmCita">
        
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Especialidad</label>
              <select class="form-select" id="especialidad" required>
                <option value="">Selecciona...</option>
                <c:forEach var="e" items="${especialidades}">
                  <option value="${e.id}">${e.nombre}</option>
                </c:forEach>
              </select>
              <div class="form-text muted">Primero elige la especialidad para listar médicos.</div>
            </div>

            <div class="col-md-6">
              <label class="form-label">Médico</label>
              <select class="form-select" name="id_medico" id="medico" required disabled>
                <option value="">Selecciona...</option>
              </select>
              <div class="form-text muted" id="infoTurno"></div>
            </div>
          </div>

          <hr class="my-3">

          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Fecha y hora</label>
              <input type="datetime-local" class="form-control" name="fecha_hora" id="fechaHora" required>
              <div class="form-text muted">Debes elegir una fecha futura dentro del turno del médico.</div>
            </div>
            <div class="col-md-6">
              <label class="form-label">Atajos de horario (según turno)</label>
              <div class="d-flex flex-wrap gap-2" id="slots">
                <span class="muted">Selecciona primero un médico…</span>
              </div>
            </div>
          </div>

          <hr class="my-3">

    
          <div class="mb-3">
            <label class="form-label">Motivo de consulta</label>
            <textarea class="form-control" name="motivo_consulta" rows="3" maxlength="255"
                      placeholder="Describe brevemente tu motivo…"></textarea>
          </div>

          <div class="d-flex gap-2">
            <button class="btn btn-primary" type="submit">Confirmar reserva</button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/mis-citas">Mis citas</a>

          </div>
          <div class="form-text mt-2">* Al confirmar, tu cita se registrará en estado <b>Programada</b>.</div>
        </form>
      </div>
    </div>
  </div>
</div>

<script>
  // ---- Datos desde BD ----
  const MEDICOS = [
    <c:forEach var="m" items="${medicos}" varStatus="status">
      {
        id_medico: ${m.idMedico}, 
        nombre: '${m.nombre}', 
        apellido: '${m.apellido}', 
        especialidad_id: ${m.especialidadId}, 
        turno: '${m.turno}', 
        horario_disponible: '${m.horarioDisponible}'
      }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];


  console.log('=== DEBUG RESERVAR DETALLADO ===');
  console.log('MEDICOS cargados:', MEDICOS);
  console.log('Total médicos:', MEDICOS.length);
  

  if (MEDICOS.length > 0) {
    console.log('Primer médico completo:', MEDICOS[0]);
    console.log('Especialidades únicas encontradas:', [...new Set(MEDICOS.map(m => m.especialidad_id))]);
    
    // Mostrar todos los médicos con sus especialidades
    MEDICOS.forEach((medico, index) => {
      console.log(`Médico ${index + 1}: ${medico.nombre} ${medico.apellido} - Especialidad ID: ${medico.especialidad_id}`);
    });
  }

  // Verificar si hay especialidades disponibles
  const selectEspecialidades = document.getElementById('especialidad');
  if (selectEspecialidades) {
    console.log('Especialidades en el select:');
    for (let i = 0; i < selectEspecialidades.options.length; i++) {
      const option = selectEspecialidades.options[i];
      console.log(`  - ${option.value}: ${option.text}`);
    }
  }


  const TURNOS = {
    'Mañana': { start: '08:00', end: '12:00' },
    'Tarde' : { start: '13:00', end: '17:00' },
    'Noche' : { start: '18:00', end: '21:00' }
  };
  
  function generarSlots(turno, cadaMin=30){
    const info = TURNOS[turno]; 
    if(!info) return [];
    const toMin = t => { const [h,m]=t.split(':').map(Number); return h*60+m; };
    const toStr = m => String(Math.floor(m/60)).padStart(2,'0')+':'+String(m%60).padStart(2,'0');
    const slots=[], ini=toMin(info.start), fin=toMin(info.end);
    for(let m=ini; m<fin; m+=cadaMin) slots.push(toStr(m));
    return slots;
  }


  const selEsp   = document.getElementById('especialidad');
  const selMed   = document.getElementById('medico');
  const infoTurn = document.getElementById('infoTurno');
  const slotsBox = document.getElementById('slots');
  const inputDT  = document.getElementById('fechaHora');


  (function setMinNow(){
    const now = new Date(); 
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    inputDT.min = now.toISOString().slice(0,16);
  })();


  selEsp?.addEventListener('change', ()=>{
    const espId = parseInt(selEsp.value);
    console.log('=== ESPECIALIDAD SELECCIONADA ===');
    console.log('ID Especialidad:', espId);
    
    // Limpiar combobox de médicos
    selMed.innerHTML = '<option value="">Selecciona...</option>';
    selMed.disabled = !espId;
    infoTurn.textContent = '';
    slotsBox.innerHTML = '<span class="muted">Selecciona primero un médico…</span>';
    
    if(!espId || isNaN(espId)) {
      console.log('No hay especialidad seleccionada o es inválida');
      return;
    }

    // Filtrar médicos por especialidad
    console.log('Filtrando médicos...');
    const medicosEncontrados = MEDICOS.filter(medico => {
      const coincide = medico.especialidad_id === espId;
      console.log(`Médico: ${medico.nombre || 'SIN_NOMBRE'} ${medico.apellido || 'SIN_APELLIDO'} - Esp ID: ${medico.especialidad_id || 'SIN_ESP_ID'} - ¿Coincide?: ${coincide}`);
      return coincide;
    });
    
    console.log('Médicos encontrados:', medicosEncontrados);
    console.log('Cantidad de médicos encontrados:', medicosEncontrados.length);
    
    if(medicosEncontrados.length === 0) {
      selMed.innerHTML = '<option value="">No hay médicos disponibles para esta especialidad</option>';
      console.log('❌ No se encontraron médicos para especialidad ID:', espId);
      return;
    }
    
    // Agregar médicos al combobox
    medicosEncontrados.forEach((medico, index) => {
      console.log(`Procesando médico ${index + 1}:`, medico);
      
      const option = document.createElement('option');
      option.value = medico.id_medico;
      option.textContent = medico.nombre + ' ' + medico.apellido;
      option.dataset.turno = medico.turno || '';
      option.dataset.horario = medico.horario_disponible || '';
      
      console.log(`Creando option: value=${option.value}, text="${option.textContent}"`);
      
      selMed.appendChild(option);
      console.log(`✅ Agregado: ${medico.nombre} ${medico.apellido}`);
    });
    
    selMed.disabled = false;
    
   
    console.log('Opciones en el select después de agregar:');
    for (let i = 0; i < selMed.options.length; i++) {
      console.log(`  Opción ${i}: value="${selMed.options[i].value}" text="${selMed.options[i].text}"`);
    }
    
    console.log('🎉 Médicos cargados correctamente en el combobox');
  });

 
  selMed?.addEventListener('change', ()=>{
    const selectedOption = selMed.selectedOptions[0];
    if(!selectedOption || !selectedOption.value){ 
      infoTurn.textContent=''; 
      slotsBox.innerHTML = '<span class="muted">Selecciona primero un médico…</span>';
      return; 
    }
    
    const turno = selectedOption.dataset.turno;
    const horario = selectedOption.dataset.horario || '';
    console.log('=== MÉDICO SELECCIONADO ===');
    console.log('Turno:', turno);
    console.log('Horario:', horario);
    
    infoTurn.textContent = turno ? `Turno ${turno}${horario ? ' — '+horario : ''}` : '';
    
    const slots = generarSlots(turno);
    slotsBox.innerHTML = '';
    if(slots.length === 0){ 
      slotsBox.innerHTML = '<span class="muted">Sin sugerencias para este turno.</span>'; 
      return; 
    }
    
    slots.forEach(hora => {
      const boton = document.createElement('button');
      boton.type = 'button'; 
      boton.className = 'slot'; 
      boton.textContent = hora;
      boton.addEventListener('click', ()=>{
        // Marcar botón activo
        document.querySelectorAll('#slots .slot').forEach(s=>s.classList.remove('active'));
        boton.classList.add('active');
        
        // Establecer fecha y hora
        let fecha = inputDT.value ? new Date(inputDT.value) : new Date();
        const [h,m] = hora.split(':').map(Number);
        fecha.setHours(h, m, 0, 0);
        
   
        if(!inputDT.value){ 
          fecha.setDate(fecha.getDate()+1); 
        }
        

        const year = fecha.getFullYear();
        const month = String(fecha.getMonth() + 1).padStart(2, '0');
        const day = String(fecha.getDate()).padStart(2, '0');
        const hours = String(fecha.getHours()).padStart(2, '0');
        const minutes = String(fecha.getMinutes()).padStart(2, '0');
        
        inputDT.value = `${year}-${month}-${day}T${hours}:${minutes}`;
        console.log('Fecha/hora establecida:', inputDT.value);
      });
      slotsBox.appendChild(boton);
    });
  });

  // Validación antes de enviar
  document.getElementById('frmCita')?.addEventListener('submit', (e)=>{
    const idMedico = selMed.value;
    if(!idMedico){ 
      e.preventDefault(); 
      alert('Selecciona un médico.'); 
      return; 
    }
    if(!inputDT.value){ 
      e.preventDefault(); 
      alert('Elige fecha y hora.'); 
      return; 
    }
    const fecha = new Date(inputDT.value);
    if(fecha < new Date()){ 
      e.preventDefault(); 
      alert('La fecha/hora debe ser futura.'); 
    }
  });
</script>

<%@ include file="/componentes/footer.jspf" %>