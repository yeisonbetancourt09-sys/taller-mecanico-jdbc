<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Error"/>
<c:set var="activo" value=""/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<h2>Ocurrió un problema</h2>
<div class="alerta alerta-error">
  <c:choose>
    <c:when test="${not empty mensajeError}"><c:out value="${mensajeError}"/></c:when>
    <c:otherwise>Se produjo un error inesperado. Intente de nuevo.</c:otherwise>
  </c:choose>
</div>
<a class="btn btn-sec" href="${pageContext.request.contextPath}/inicio">Volver al inicio</a>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
