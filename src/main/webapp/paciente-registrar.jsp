<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle","Registrar Paciente | Home Safety"); %>
<%@ include file="/componentes/header.jspf" %>

<div class="row justify-content-center">
  <div class="col-lg-10">
    <h1 class="h5 mb-3">Registrar paciente</h1>

 
    <jsp:include page="/componentes/form-paciente.jsp">
      <jsp:param name="accion" value="${pageContext.request.contextPath}/PacienteRegistra"/>
    </jsp:include>
  </div>
</div>

<%@ include file="/componentes/footer.jspf" %>