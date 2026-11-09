<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
request.setAttribute("pageTitle", "Iniciar sesión | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<div class="row justify-content-center my-5">
	<div class="col-md-5">
		<div class="card card-soft">
			<div class="card-body p-4">
				<h1 class="h4 mb-3 text-center">Iniciar sesión</h1>


				<c:if test="${not empty param.msg}">
					<div class="alert alert-success py-2">${param.msg}</div>
				</c:if>
				<c:if test="${not empty error}">
					<div class="alert alert-danger py-2">${error}</div>
				</c:if>


				<form method="post"
					action="${pageContext.request.contextPath}/login">
					<div class="mb-3">
						<label class="form-label">Usuario o correo</label> <input
							type="text" name="username" class="form-control"
							placeholder="usuario o correo" required autofocus>
					</div>

					<div class="mb-2">
						<label class="form-label d-flex justify-content-between">
							<span>Contraseña</span>
							<button type="button" class="btn btn-link p-0 small"
								id="togglePass">Mostrar</button>
						</label> <input type="password" name="password" id="password"
							class="form-control" placeholder="••••••••" required>
					</div>

					<div class="d-flex justify-content-between align-items-center mb-3">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="remember">
							<label class="form-check-label small" for="remember">Recordarme</label>
						</div>
						<a class="small" href="#">¿Olvidaste tu contraseña?</a>
					</div>

					<button class="btn btn-primary w-100">Ingresar</button>
				</form>

				<div class="text-center mt-3">
					<a class="btn btn-outline-secondary w-100"
						href="${pageContext.request.contextPath}/register.jsp">Crear
						cuenta</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
  
  document.getElementById('togglePass')?.addEventListener('click', function(){
    const input = document.getElementById('password');
    if(!input) return;
    const show = input.type === 'password';
    input.type = show ? 'text' : 'password';
    this.textContent = show ? 'Ocultar' : 'Mostrar';
  });
</script>

<%@ include file="/componentes/footer.jspf"%>