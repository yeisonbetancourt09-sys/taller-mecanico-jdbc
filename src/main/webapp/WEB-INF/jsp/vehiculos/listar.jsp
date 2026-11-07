<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Vehículos"/>
<c:set var="activo" value="vehiculos"/>
<c:set var="base" value="${pageContext.request.contextPath}/vehiculos"/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<div class="barra">
  <h2>Vehículos</h2>

  <%-- Formulario con método GET: filtra por cliente --%>
  <form method="get" action="${base}" class="buscador">
    <select name="idCliente">
      <option value="0">Todos los clientes</option>
      <c:forEach var="cli" items="${clientes}">
        <option value="${cli.idCliente}" ${param.idCliente == cli.idCliente ? 'selected' : ''}>
          <c:out value="${cli.nombre}"/>
        </option>
      </c:forEach>
    </select>
    <button type="submit" class="btn btn-sec">Filtrar</button>
  </form>

  <a class="btn btn-primary" href="${base}?accion=nuevo">+ Nuevo vehículo</a>
</div>

<div class="tabla-envoltura">
  <table class="tabla">
    <thead>
      <tr><th>Placa</th><th>Marca</th><th>Modelo</th><th>Año</th><th>Color</th><th>Propietario</th><th>Acciones</th></tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty vehiculos}">
          <tr><td colspan="7" class="vacio">No hay vehículos para mostrar.</td></tr>
        </c:when>
        <c:otherwise>
          <c:forEach var="veh" items="${vehiculos}">
            <tr>
              <td><span class="placa"><c:out value="${veh.placa}"/></span></td>
              <td><c:out value="${veh.marca}"/></td>
              <td><c:out value="${veh.modelo}"/></td>
              <td><c:out value="${veh.anio}"/></td>
              <td><c:out value="${veh.color}"/></td>
              <td><c:out value="${nombresClientes[veh.idCliente]}"/></td>
              <td class="acciones">
                <a class="btn btn-sm btn-sec" href="${base}?accion=editar&id=${veh.idVehiculo}">Editar</a>
                <%-- Formulario con método POST: eliminar --%>
                <form method="post" action="${base}" class="en-linea"
                      onsubmit="return confirm('¿Eliminar este vehículo?');">
                  <input type="hidden" name="accion" value="eliminar">
                  <input type="hidden" name="id" value="${veh.idVehiculo}">
                  <button type="submit" class="btn btn-sm btn-peligro">Eliminar</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
