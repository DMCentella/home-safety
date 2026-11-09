# Stored Procedures (SP) and SQL Statements by Method

Este documento lista todos los procedimientos almacenados (SP) y sentencias SQL utilizadas en cada método de los DAOs del proyecto.

---

## HistorialMedicoDAOImpl.java

- **crearHistorial**  
  SP: `sp_historial_crear(?,?,?,?)`

- **obtenerPorCita**  
  SQL: `SELECT * FROM historial_medico WHERE id_cita=? ORDER BY fecha_registro DESC LIMIT 1`

- **actualizar**  
  SQL: `UPDATE historial_medico SET notas=?, archivos_adjuntos=? WHERE id_historial=?`

- **listarPorPaciente**  
  SQL: `SELECT * FROM historial_medico WHERE id_paciente=? ORDER BY fecha_registro DESC`

---

## CitaDAOImpl.java

- **existeChoque**  
  SP: `sp_citas_existe_choque(?,?,?)`

- **crear**  
  SP: `sp_citas_crear(?,?,?,?,?)`

- **listarPorPaciente**  
  SP: `sp_citas_listar_por_paciente(?,?,?,?)`

- **reprogramar**  
  SP: `sp_citas_reprogramar(?,?,?)`

- **actualizarEstado**  
  SP: `sp_citas_actualizar_estado(?,?,?)`

- **kpisDiaMedico**  
  SP: `sp_citas_kpis_dia_medico(?,?)`

- **citasDiaMedico**  
  SP: `sp_citas_dia_medico(?,?)`

- **crearParaPacientes**  
  SP: `sp_cita_crear_para_paciente(?,?,?,?)`

- **esDePaciente**  
  SQL: `SELECT 1 FROM citas WHERE id_cita=? AND id_paciente=? LIMIT 1`

- **esDeMedico**  
  SQL: `SELECT 1 FROM citas WHERE id_cita=? AND id_medico=? LIMIT 1`

- **pacienteIdPorCita**  
  SQL: `SELECT id_paciente FROM citas WHERE id_cita=?`

---

## PacienteDAOImpl.java

- **findIdByUsuario**  
  SP: `sp_pacientes_find_id_by_usuario(?)`

- **creaDesdeElUsuario**  
  SP: `sp_pacientes_crear_desde_usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)`

- **obtener**  
  SQL: `SELECT * FROM pacientes WHERE id_paciente=?`

- **crearManual**  
  SP: `sp_paciente_crear_manual(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,,,?)`

- **buscarPorDni**  
  SQL: `SELECT * FROM pacientes WHERE dni = ?`

- **buscarPorIdUsuario**  
  SQL: `SELECT id_paciente, dni, nombre, apellido FROM pacientes WHERE id_usuario = ?`

- **vincularUsuarioPorDni**  
  SP: `sp_paciente_vincular_usuario(?,?)`

- **actualizarContacto**  
  SQL: `UPDATE pacientes SET telefono=?, email=? WHERE id_paciente=?`

- **buscar**  
  SQL:  
  ```sql
  SELECT * FROM pacientes
  WHERE dni LIKE ? OR nombre LIKE ? OR apellido LIKE ?
  ORDER BY fecha_registro DESC
  LIMIT ? OFFSET ?
  ```

- **contar**  
  SQL:  
  `SELECT COUNT(*) FROM pacientes WHERE dni LIKE ? OR nombre LIKE ? OR apellido LIKE ?`

- **actualizarBasicos**  
  SQL:  
  ```sql
  UPDATE pacientes SET
    telefono=?,
    email=?,
    seguro_medico=?,
    direccion_residencia=?,
    depto_residencia=?,
    prov_residencia=?,
    dist_residencia=?,
    num_departamento=?,
    urbanizacion=?
  WHERE id_paciente=?
  ```

---

## MedicoDAOImpl.java

- **obtener**  
  SP: `sp_medicos_obtener(?)`

- **listarPorEspecialidad**  
  SP: `sp_medicos_listar_por_especialidad(?)`

- **listarTodos**  
  SQL: `SELECT * FROM medicos ORDER BY apellido, nombre`

---

## EspecialidadDAOImpl.java

- **listarToda**  
  SP: `sp_especialidades_listar()`

- **obtener**  
  SP: `sp_especialidades_obtener(?)`

---

## AsignacionDAOImpl.java

- **buscarOCrear**  
  SP: `sp_asignaciones_buscar_o_crear(?,?,?,?)`

---

## ReporteDAOImpl.java

- **kpiS**  
  SP: `sp_reportes_kpis(?,?,?,?)`

- **contar**  
  SP: `sp_reportes_contar(?,?,?,?)`

- **listar**  
  SP: `sp_reportes_listar(?,?,?,?,?,?)`

---

## UsuarioDAOImpl.java

- **findByUser**  
  SQL: `SELECT * FROM usuarios WHERE username=? OR email=? LIMIT 1`

- **rolesDeUsuario**  
  SP: `sp_usuario_roles(?)`

- **idMedicoPorUsuario**  
  SP: `sp_usuario_id_medico_por_usuario(?)`

- **idPacientePorUsuario**  
  SP: `sp_usuario_id_paciente_por_usuario(?)`

- **existsUsername**  
  SQL: `SELECT 1 FROM usuarios WHERE username=? LIMIT 1`

- **existsEmail**  
  SQL: `SELECT 1 FROM usuarios WHERE email=? LIMIT 1`

- **crearUsuario**  
  SQL: `INSERT INTO usuarios(username,email,password_hash,estado) VALUES(?,?,?,1)`

- **asignarRol**  
  SQL:  
  ```sql
  INSERT IGNORE INTO usuario_roles(id_usuario, id_rol)
  SELECT ?, r.id FROM roles r WHERE r.nombre=? LIMIT 1
  ```

- **listarTodos**  
  SQL:  
  ```sql
  SELECT id_usuario, username, password_hash, email, estado, fecha_registro
  FROM usuarios ORDER BY username
  ```

- **actualizarRoles**  
  SQL:  
  - `DELETE FROM usuario_roles WHERE id_usuario=?`
  - `INSERT INTO usuario_roles(id_usuario, id_rol) SELECT ?, r.id FROM roles r WHERE r.nombre=?`

---

## VinculacionDAOImpl.java

- **vincularMedicoAUsuario**  
  SQL:  
  - `UPDATE medicos SET id_usuario=NULL WHERE id_usuario=?`
  - `UPDATE medicos SET id_usuario=? WHERE id_medico=?`

- **limpiarVinculoMedicoDeUsuario**  
  SQL: `UPDATE medicos SET id_usuario=NULL WHERE id_usuario=?`

- **desvincularMedicoDeUsuario**  
  SQL: `UPDATE medicos SET id_usuario=NULL WHERE id_medico=?`

- **vincularPacienteAUsuario**  
  SQL:  
  - `UPDATE pacientes SET id_usuario=NULL WHERE id_usuario=?`
  - `UPDATE pacientes SET id_usuario=? WHERE id_paciente=?`

- **limpiarVinculoPacienteDeUsuario**  
  SQL: `UPDATE pacientes SET id_usuario=NULL WHERE id_usuario=?`

- **desvincularPacienteDeUsuario**  
  SQL: `UPDATE pacientes SET id_usuario=NULL WHERE id_paciente=?`

---

> Documento generado automáticamente.
