<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Cliente"/>
<c:set var="activo" value="clientes"/>
<c:set var="base" value="${pageContext.request.contextPath}/clientes"/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<h2><c:out value="${cliente.idCliente == 0 ? 'Nuevo cliente' : 'Editar cliente'}"/></h2>

<c:if test="${not empty error}">
  <div class="alerta alerta-error"><c:out value="${error}"/></div>
</c:if>

<%-- Formulario con método POST: insertar o actualizar --%>
<form method="post" action="${base}" class="formulario">
  <input type="hidden" name="accion" value="guardar">
  <input type="hidden" name="idCliente" value="${cliente.idCliente}">

  <div class="campo">
    <label for="documento">Documento *</label>
    <input type="text" id="documento" name="documento" maxlength="20" required
           value="<c:out value='${cliente.documento}'/>">
  </div>
  <div class="campo">
    <label for="nombre">Nombre completo *</label>
    <input type="text" id="nombre" name="nombre" maxlength="100" required
           value="<c:out value='${cliente.nombre}'/>">
  </div>
  <div class="campo">
    <label for="telefono">Teléfono *</label>
    <input type="tel" id="telefono" name="telefono" maxlength="20" required
           value="<c:out value='${cliente.telefono}'/>">
  </div>
  <div class="campo">
    <label for="email">Correo electrónico</label>
    <input type="email" id="email" name="email" maxlength="100"
           value="<c:out value='${cliente.email}'/>">
  </div>
  <div class="campo campo-completo">
    <label for="direccion">Dirección</label>
    <input type="text" id="direccion" name="direccion" maxlength="150"
           value="<c:out value='${cliente.direccion}'/>">
  </div>

  <div class="botones">
    <a class="btn btn-sec" href="${base}">Cancelar</a>
    <button type="submit" class="btn btn-primary">Guardar</button>
  </div>
</form>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
