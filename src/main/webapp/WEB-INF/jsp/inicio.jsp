<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Inicio"/>
<c:set var="activo" value="inicio"/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<h2>Resumen general del taller</h2>

<c:if test="${not empty errorConexion}">
  <div class="alerta alerta-error">No fue posible consultar la base de datos: <c:out value="${errorConexion}"/></div>
</c:if>

<div class="tarjetas">
  <div class="tarjeta">
    <span class="etiqueta">Total clientes</span>
    <span class="valor"><c:out value="${empty totalClientes ? '-' : totalClientes}"/></span>
    <a href="${pageContext.request.contextPath}/clientes">Ver clientes →</a>
  </div>
  <div class="tarjeta">
    <span class="etiqueta">Total vehículos</span>
    <span class="valor"><c:out value="${empty totalVehiculos ? '-' : totalVehiculos}"/></span>
    <a href="${pageContext.request.contextPath}/vehiculos">Ver vehículos →</a>
  </div>
</div>

<div class="acciones-rapidas">
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/clientes?accion=nuevo">+ Nuevo cliente</a>
  <a class="btn btn-sec" href="${pageContext.request.contextPath}/vehiculos?accion=nuevo">+ Nuevo vehículo</a>
</div>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
