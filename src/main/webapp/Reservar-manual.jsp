<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% request.setAttribute("pageTitle","Reservar Cita (Recepción) | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<style>
  .card-soft{border:1px solid rgba(0,0,0,.06); box-shadow:0 10px 30px rgba(0,0,0,.05)}
  .slot{padding:.45rem .7rem; border:1px solid #d0d7e2; border-radius:.5rem; background:#fff; cursor:pointer}
  .slot:hover{background:#f5f9ff}
  .slot.active{background:#0b6efd; color:#fff; border-color:#0b6efd}
  .muted{color:#6b7280}
</style>

<div class="row justify-content-center">
  <div class="col-xl-11">
    <div class="card card-soft">
      <div class="card-body">
        <div class="d-flex justify-content-between align-items-center">
          <h1 class="h5 mb-3">Reservar cita — Recepción</h1>
          <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/paginas/pacientes-nuevo.jsp">
            + Nuevo paciente
          </a>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/CrearCitaRecepcion" id="frmCita">
        
          <input type="hidden" name="id_paciente" id="idPaciente"/>

     
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Paciente</label>
              <div class="input-group">
                <input class="form-control" id="pacienteNombre" placeholder="—" disabled>
                <button class="btn btn-outline-danger" type="button" id="btnLimpiarPac">Quitar</button>
              </div>
            </div>
          </div>

          <hr class="my-3">

       
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Especialidad</label>
              <select class="form-select" id="especialidad" required>
                <option value="">Selecciona...</option>
                <c:forEach var="e" items="${especialidades}">
                  <option value="${e.id}">${e.nombre}</option>
                </c:forEach>
              </select>
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
              <div class="form-text muted">Debe ser una fecha futura y dentro del turno.</div>
            </div>
            <div class="col-md-6">
              <label class="form-label">Atajos de horario</label>
              <div class="d-flex flex-wrap gap-2" id="slots">
                <span class="muted">Selecciona médico…</span>
              </div>
            </div>
          </div>

          <hr class="my-3">

  
          <div class="mb-3">
            <label class="form-label">Motivo de consulta</label>
            <textarea class="form-control" name="motivo_consulta" rows="3" maxlength="255"></textarea>
          </div>

          <div class="d-flex gap-2">
            <button class="btn btn-primary" type="submit">Confirmar reserva</button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/reportes-citas-fechas.jsp">Ver agenda</a>
          </div>
          <div class="form-text mt-2">Estado inicial: <b>Programada</b>.</div>
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
  
 
    MEDICOS.forEach((medico, index) => {
      console.log(`Médico ${index + 1}: ${medico.nombre} ${medico.apellido} - Especialidad ID: ${medico.especialidad_id}`);
    });
  }

  const selectEspecialidades = document.getElementById('especialidad');
  if (selectEspecialidades) {
    console.log('Especialidades en el select:');
    for (let i = 0; i < selectEspecialidades.options.length; i++) {
      const option = selectEspecialidades.options[i];
      console.log(`  - ${option.value}: ${option.text}`);
    }
  }
</script>

<c:if test="${not empty pacienteSel}">
  <script>
    document.addEventListener('DOMContentLoaded', () => {
      const id = ${pacienteSel.idPaciente};
      const texto = '${fn:escapeXml(pacienteSel.dni)} — ${fn:escapeXml(pacienteSel.nombre)} ${fn:escapeXml(pacienteSel.apellido)}';
      document.getElementById('idPaciente').value = id;
      document.getElementById('pacienteNombre').value = texto;
    });
  </script>
</c:if>

<script>
  // ====== Constantes de turnos ======
  const TURNOS = { 'Mañana':{start:'08:00',end:'12:00'}, 'Tarde':{start:'13:00',end:'17:00'}, 'Noche':{start:'18:00',end:'21:00'} };
  const toMin = t => { const [h,m]=t.split(':').map(Number); return h*60+m; };
  const toStr = m => String(Math.floor(m/60)).padStart(2,'0')+':'+String(m%60).padStart(2,'0');
  function generarSlots(turno, cadaMin=30){ const i=TURNOS[turno]; if(!i) return []; const a=toMin(i.start), b=toMin(i.end); const out=[]; for(let m=a;m<b;m+=cadaMin) out.push(toStr(m)); return out; }


  const selEsp   = document.getElementById('especialidad');
  const selMed   = document.getElementById('medico');
  const infoTurn = document.getElementById('infoTurno');
  const slotsBox = document.getElementById('slots');
  const inputDT  = document.getElementById('fechaHora');
  const idPacInp = document.getElementById('idPaciente');
  const pacNombre= document.getElementById('pacienteNombre');
  const btnClr   = document.getElementById('btnLimpiarPac');


  (function(){
    const now = new Date(); now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    inputDT.min = now.toISOString().slice(0,16);
  })();

  // Botón limpiar paciente
  btnClr.addEventListener('click', ()=>{ 
    idPacInp.value=''; 
    pacNombre.value=''; 
  });

  // Especialidad → médicos
  selEsp.addEventListener('change', ()=>{
    const espId = parseInt(selEsp.value);
    console.log('=== ESPECIALIDAD SELECCIONADA ===');
    console.log('ID Especialidad:', espId);
    
    // Limpiar combobox de médicos
    selMed.innerHTML = '<option value="">Selecciona...</option>';
    selMed.disabled = !espId;
    infoTurn.textContent = '';
    slotsBox.innerHTML = '<span class="muted">Selecciona médico…</span>';
    
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
    
    // Verificar si las opciones se agregaron correctamente
    console.log('Opciones en el select después de agregar:');
    for (let i = 0; i < selMed.options.length; i++) {
      console.log(`  Opción ${i}: value="${selMed.options[i].value}" text="${selMed.options[i].text}"`);
    }
    
    console.log('🎉 Médicos cargados correctamente en el combobox');
  });

  // Médico → turno y slots
  selMed.addEventListener('change', ()=>{
    const opt = selMed.selectedOptions[0];
    if(!opt || !opt.value){ 
      infoTurn.textContent=''; 
      slotsBox.innerHTML = '<span class="muted">Selecciona médico…</span>';
      return; 
    }
    const turno = opt.dataset.turno, horario = opt.dataset.horario || '';
    infoTurn.textContent = turno ? `Turno ${turno}${horario ? ' — '+horario : ''}` : '';
    const arr = generarSlots(turno);
    slotsBox.innerHTML = '';
    if(arr.length === 0){ 
      slotsBox.innerHTML = '<span class="muted">Sin sugerencias para este turno.</span>'; 
      return; 
    }
    arr.forEach(hh=>{
      const b = document.createElement('button');
      b.type='button'; b.className='slot'; b.textContent=hh;
      b.addEventListener('click', ()=>{
        document.querySelectorAll('#slots .slot').forEach(s=>s.classList.remove('active'));
        b.classList.add('active');
        const d = inputDT.value ? new Date(inputDT.value) : new Date();
        const [h,m] = hh.split(':').map(Number);
        d.setHours(h, m, 0, 0);
        d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
        if(!inputDT.value){ d.setDate(d.getDate()+1); }
        inputDT.value = d.toISOString().slice(0,16);
      });
      slotsBox.appendChild(b);
    });
  });

  // Validación final
  document.getElementById('frmCita').addEventListener('submit', (e)=>{
    if(!idPacInp.value){ e.preventDefault(); alert('Seleccione un paciente.'); return; }
    if(!selMed.value){ e.preventDefault(); alert('Seleccione un médico.'); return; }
    if(!inputDT.value){ e.preventDefault(); alert('Elija fecha y hora.'); return; }
    const d = new Date(inputDT.value);
    if(d < new Date()){ e.preventDefault(); alert('La fecha/hora debe ser futura.'); }
  });
</script>

<%@ include file="/componentes/footer.jspf" %>