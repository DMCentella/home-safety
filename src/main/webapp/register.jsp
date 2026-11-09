<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("pageTitle","Crear cuenta | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<div class="row justify-content-center">
  <div class="col-lg-6">
    <div class="card card-soft">
      <div class="card-body p-4">
        <h1 class="h4 mb-3">Crear cuenta</h1>

        <!-- Mensajes -->
        <c:if test="${not empty error}">
          <div class="alert alert-danger py-2">${error}</div>
        </c:if>
        <c:if test="${not empty param.msg}">
          <div class="alert alert-success py-2">${param.msg}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Usuario</label>
              <input type="text" class="form-control" name="username" placeholder="Usuario" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Correo</label>
              <input type="email" class="form-control" name="email" placeholder="tucorreo@homesafety.pe" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Contraseña</label>
              <input type="password" class="form-control" name="password" placeholder="Mínimo 6 caracteres" minlength="6" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Confirmar contraseña</label>
              <input type="password" class="form-control" name="confirm" placeholder="Repítela" minlength="6" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Tipo de usuario</label>
              <select class="form-select" name="tipo" required>
                <option value="">Selecciona...</option>
                <option value="PACIENTE">Paciente</option>
                <option value="DOCTOR">Doctor</option>
                <option value="ADMIN">Administrador</option>
              </select>
            </div>
            <div class="col-12">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="acepto" required>
                <label class="form-check-label" for="acepto">
                  Acepto los <a href="#" class="text-decoration-underline">Términos</a> y la <a href="#" class="text-decoration-underline">Política de Privacidad</a>.
                </label>
              </div>
            </div>
          </div>
          <div class="d-grid gap-2 mt-3">
            <button class="btn btn-success" type="submit">Crear cuenta</button>
            <a class="btn btn-outline-secondary" href="login.jsp">Ya tengo cuenta</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%@ include file="/componentes/footer.jspf" %>